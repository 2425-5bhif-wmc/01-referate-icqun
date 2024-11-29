package at.htl.feature;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/user")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @WithSession
    public Uni<List<UserDto>> getAll() {
        return userRepository.listAll()
                .onItem().transform(users ->
                        users
                                .stream()
                                .map(user -> new UserDto(
                                        user.getId(),
                                        user.getFirstName(),
                                        user.getLastName(),
                                        user.getEmail())
                                ).toList()
                );
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @WithSession
    public Uni<UserDto> getById(@PathParam("id") Long id) {
        return userRepository.findById(id)
                .onItem().transform(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail())
                );
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @WithTransaction
    public Uni<Response> createNew(User user, @Context UriInfo uriInfo) {
        return Uni.createFrom()
                .item(user)
                .onItem().ifNull().failWith(new WebApplicationException(Response.Status.BAD_REQUEST))
                .onItem().ifNotNull().transformToUni(ignored -> userRepository.persistAndFlush(user))
                .onItem().transform(u -> {
                    URI uri = uriInfo
                            .getAbsolutePathBuilder()
                            .path(Long.toString(u.getId()))
                            .build();

                    return Response
                            .created(uri)
                            .entity(u)
                            .build();
                });
    }
}

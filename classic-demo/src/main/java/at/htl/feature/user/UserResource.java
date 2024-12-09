package at.htl.feature.user;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    public List<UserDto> getAll() {
        return userRepository.listAll()
                .stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                ))
                .toList();
    }

    // tag::get_simple_classic[]
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getById(@PathParam("id") Long id) {
        // = Blocking=
        User user = userRepository.findById(id);
        // = Blocking =

        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
    // end::get_simple_classic[]

    // tag::create_classic[]
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createNew(User user, @Context UriInfo uriInfo) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // = Blocking =
        userRepository.persistAndFlush(user);
        // = Blocking =

        URI uri = uriInfo
                .getAbsolutePathBuilder()
                .path(Long.toString(user.getId()))
                .build();

        return Response
                .created(uri)
                .entity(user)
                .build();
    }
    // end::create_classic[]
}

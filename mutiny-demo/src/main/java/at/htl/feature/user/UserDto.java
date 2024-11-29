package at.htl.feature.user;

public record UserDto(
    Long id,
    String firstname,
    String lastname,
    String email
) {
}

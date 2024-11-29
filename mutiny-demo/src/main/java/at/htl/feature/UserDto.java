package at.htl.feature;

public record UserDto(
    Long id,
    String firstname,
    String lastname,
    String email
) {
}

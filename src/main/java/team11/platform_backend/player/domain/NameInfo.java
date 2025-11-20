package team11.platform_backend.player.domain;

public record NameInfo(
        String firstName,
        String lastName,
        String username
) {
    public NameInfo(String firstName, String lastName, String username) {
        if (firstName == null || lastName == null || username == null) {
            throw new IllegalArgumentException("NameInfo cannot be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}

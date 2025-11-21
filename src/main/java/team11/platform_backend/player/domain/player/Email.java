package team11.platform_backend.player.domain.player;

import java.util.regex.Pattern;

public record Email(String emailAddress) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    );

    public Email {
        validateEmail(emailAddress);
        // Normalize to lowercase for consistency
        emailAddress = emailAddress.trim().toLowerCase();
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        String trimmed = email.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (trimmed.length() > 254) {
            throw new IllegalArgumentException("Email address too long");
        }

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        // Check for consecutive dots
        if (trimmed.contains("..")) {
            throw new IllegalArgumentException("Email cannot contain consecutive dots");
        }

        // Check local part length (before @)
        String localPart = trimmed.substring(0, trimmed.indexOf('@'));
        if (localPart.length() > 64) {
            throw new IllegalArgumentException("Email local part too long");
        }
    }

    public String getLocalPart() {
        return emailAddress.substring(0, emailAddress.indexOf('@'));
    }

    public String getDomain() {
        return emailAddress.substring(emailAddress.indexOf('@') + 1);
    }

}

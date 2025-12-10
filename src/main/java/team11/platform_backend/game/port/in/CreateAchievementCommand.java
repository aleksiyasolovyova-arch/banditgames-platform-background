package team11.platform_backend.game.port.in;

public record CreateAchievementCommand(
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {

    public CreateAchievementCommand {
        // Achievement Name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement name cannot be empty");
        }
        name = name.trim();
        if (name.length() > 100) {
            throw new IllegalArgumentException("Achievement name cannot exceed 100 characters");
        }

        // Achievement Description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement description cannot be empty");
        }
        if (description.length() > 255) {
            throw new IllegalArgumentException("Achievement description cannot exceed 255 characters");
        }

        // Picture URL
        if (pictureUrl == null || pictureUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Picture URL cannot be empty");
        }

        // Achievement Type
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement type cannot be empty");
        }

        // Threshold
        if (requiredValue < 0 || requiredValue > 100) {
            throw new IllegalArgumentException("Achievement required value must be between 0 and 100");
        }
    }

}

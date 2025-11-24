package team11.platform_backend.game.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAchievementCommand(
        UUID gameId,
        String achievementName,
        String achievementDescription,
        String pictureUrl,
        String achievementType,
        BigDecimal threshold
) {

    public CreateAchievementCommand {
        // Game ID
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        // Achievement Name
        if (achievementName == null || achievementName.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement name cannot be empty");
        }
        achievementName = achievementName.trim();
        if (achievementName.length() > 100) {
            throw new IllegalArgumentException("Achievement name cannot exceed 100 characters");
        }

        // Achievement Description
        if (achievementDescription == null || achievementDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement description cannot be empty");
        }
        if (achievementDescription.length() > 255) {
            throw new IllegalArgumentException("Achievement description cannot exceed 255 characters");
        }

        // Picture URL
        if (pictureUrl == null || pictureUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Picture URL cannot be empty");
        }

        // Achievement Type
        if (achievementType == null || achievementType.trim().isEmpty()) {
            throw new IllegalArgumentException("Achievement type cannot be empty");
        }

        // Threshold
        if (threshold == null) {
            throw new IllegalArgumentException("Threshold cannot be null");
        }
        if (threshold.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Threshold must be greater than 0");
        }
    }

}

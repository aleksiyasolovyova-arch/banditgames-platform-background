package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementTypeException;
import org.springframework.util.Assert;

public record CreateAchievementCommand(
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {

    public CreateAchievementCommand {
        if (name == null || name.isBlank()) {
            throw new InvalidAchievementException("Achievement name cannot be empty");
        }
        if (name.length() > 100) {
            throw new InvalidAchievementException("Achievement name cannot exceed 100 characters");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidAchievementException("Achievement description cannot be empty");
        }
        if (description.length() > 255) {
            throw new InvalidAchievementException("Achievement description cannot exceed 255 characters");
        }
        if (pictureUrl == null || pictureUrl.isBlank()) {
            throw new InvalidAchievementException("Picture URL cannot be empty");
        }
        if (type == null || type.isBlank()) {
            throw new InvalidAchievementTypeException("Achievement type cannot be empty");
        }
        if (requiredValue < 0 || requiredValue > 100) {
            throw new InvalidAchievementException("Achievement required value must be between 0 and 100");
        }
    }


}

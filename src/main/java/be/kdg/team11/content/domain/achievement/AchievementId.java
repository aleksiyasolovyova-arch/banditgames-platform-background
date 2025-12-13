package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }

    public static AchievementId of(UUID uuid) {
        if (uuid == null) {
            throw new InvalidAchievementException("Achievement ID UUID cannot be null");
        }
        return new AchievementId(uuid);
    }

    public static InvalidAchievementException notFound(AchievementId achievementId) {
        return new InvalidAchievementException(
                String.format("Achievement not found with ID: %s", achievementId.achievementId())
        );
    }

}

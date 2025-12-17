package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.InvalidAchievementForPlayerException;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public AchievementId {
        if (achievementId == null) {
            throw new InvalidAchievementForPlayerException("Achievement ID cannot be null");
        }
    }

    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }

    public static AchievementId of(UUID achievementId) {
        return new AchievementId(achievementId);
    }
}

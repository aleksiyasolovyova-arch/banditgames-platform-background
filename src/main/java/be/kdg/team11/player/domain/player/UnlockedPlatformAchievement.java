package be.kdg.team11.player.domain.player;


import be.kdg.team11.player.domain.player.exceptions.InvalidAchievementForPlayerException;

import java.time.LocalDateTime;

public record UnlockedPlatformAchievement(
        AchievementId achievementId,
        LocalDateTime unlockedAt
) {

    public UnlockedPlatformAchievement {
        if (achievementId == null) {
            throw new InvalidAchievementForPlayerException("Achievement ID cannot be null");
        }
        if (unlockedAt == null) {
            throw new InvalidAchievementForPlayerException("Unlocked at time cannot be null");
        }
    }

    /**
     * Factory method for creating a newly unlocked achievement.
     */
    public static UnlockedPlatformAchievement now(AchievementId achievementId) {
        return new UnlockedPlatformAchievement(achievementId, LocalDateTime.now());
    }

    /**
     * Factory method for creating achievement from storage.
     */
    public static UnlockedPlatformAchievement at(AchievementId achievementId, LocalDateTime unlockedAt) {
        return new UnlockedPlatformAchievement(achievementId, unlockedAt);
    }
}

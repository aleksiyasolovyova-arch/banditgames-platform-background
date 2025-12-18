package be.kdg.team11.player.domain.player;

import java.time.LocalDateTime;

public record UnlockedPlatformAchievement(
        AchievementId achievementId,
        LocalDateTime unlockedAt
) {

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

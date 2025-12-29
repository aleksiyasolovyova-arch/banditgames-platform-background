package be.kdg.team11.player.domain.player;

import java.time.LocalDateTime;

public record UnlockedPlatformAchievement(
        PlatformAchievementId platformAchievementId,
        LocalDateTime unlockedAt
) {

    /**
     * Factory method for creating a newly unlocked achievement.
     */
    public static UnlockedPlatformAchievement now(PlatformAchievementId platformAchievementId) {
        return new UnlockedPlatformAchievement(platformAchievementId, LocalDateTime.now());
    }

    /**
     * Factory method for creating achievement from storage.
     */
    public static UnlockedPlatformAchievement at(PlatformAchievementId platformAchievementId, LocalDateTime unlockedAt) {
        return new UnlockedPlatformAchievement(platformAchievementId, unlockedAt);
    }
}

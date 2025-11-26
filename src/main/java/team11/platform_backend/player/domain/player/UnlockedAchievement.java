package team11.platform_backend.player.domain.player;

import java.time.LocalDateTime;


public record UnlockedAchievement(
        AchievementId achievementId,
        LocalDateTime unlockedAt
) {
    public UnlockedAchievement {
        if (achievementId == null)
            throw new IllegalArgumentException("achievementId cannot be null");
        if (unlockedAt == null)
            throw new IllegalArgumentException("unlockedAt cannot be null");
    }
}


package team11.platform_backend.player.domain.player;

import java.time.LocalDateTime;


public record UnlockedGameAchievement(
        String achievementCode,
        LocalDateTime unlockedAt
) {
    public UnlockedGameAchievement {
        if (achievementCode.isEmpty())
            throw new IllegalArgumentException("Achievement code cannot be empty");
        if (unlockedAt == null)
            throw new IllegalArgumentException("unlockedAt cannot be null");
    }
}


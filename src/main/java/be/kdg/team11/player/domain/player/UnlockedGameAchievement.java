package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDateTime;


public record UnlockedGameAchievement(
        GameReference gameReference,
        String code,
        LocalDateTime unlockedAt
) {
    public UnlockedGameAchievement {
        if (code.isEmpty())
            throw new IllegalArgumentException("Achievement code cannot be empty");
        if (unlockedAt == null)
            throw new IllegalArgumentException("unlockedAt cannot be null");
    }
}


package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.InvalidAchievementForPlayerException;
import be.kdg.team11.player.domain.player.exceptions.InvalidGameForPlayerException;
import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDateTime;


public record UnlockedGameAchievement(
        GameReference gameReference,
        String code,
        LocalDateTime unlockedAt
) {
    public UnlockedGameAchievement {
        if (gameReference == null) {
            throw new InvalidGameForPlayerException("Game reference cannot be null");
        }
        if (code == null || code.isEmpty()) {
            throw new InvalidAchievementForPlayerException("Achievement code cannot be empty");
        }
        if (unlockedAt == null) {
            throw new InvalidAchievementForPlayerException("Unlocked at time cannot be null");
        }
    }

    /**
     * Factory method for creating a newly unlocked game achievement.
     */
    public static UnlockedGameAchievement now(GameReference gameReference, String code) {
        return new UnlockedGameAchievement(gameReference, code, LocalDateTime.now());
    }

    /**
     * Factory method for creating achievement from storage.
     */
    public static UnlockedGameAchievement at(GameReference gameReference, String code, LocalDateTime unlockedAt) {
        return new UnlockedGameAchievement(gameReference, code, unlockedAt);
    }
}


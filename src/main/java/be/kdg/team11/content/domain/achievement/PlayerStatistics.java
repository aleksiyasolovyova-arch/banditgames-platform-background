// game/domain/achievement/PlayerStatistics.java
package be.kdg.team11.content.domain.achievement;


import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;

import java.util.UUID;

/**
 * Value Object representing player statistics used to evaluate achievement criteria.
 * Immutable snapshot of a player's performance metrics.
 * Note: Statistics can be null if the player hasn't yet engaged in that activity.
 * For example, a new player may have null totalWins until they complete their first game.
 */
 public record PlayerStatistics(
        Long totalGamesPlayed,
        Long totalWins,
        Long totalFriends,
        Long bestRecordTime,
        UUID playerId
) {
    // For each event type add a mapper method

/**
 * Compact constructor - validates that playerId is present.
 * Other statistics can be null (player hasn't engaged in that activity yet).
 */
 public PlayerStatistics {
        if (playerId == null) {
            throw new InvalidAchievementException("Player ID cannot be null in statistics");
        }
    }
}
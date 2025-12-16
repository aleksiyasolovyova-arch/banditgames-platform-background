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

public static PlayerStatistics of(
        Long totalGamesPlayed,
        Long totalWins,
        Long totalFriends,
        Long bestRecordTime,
        UUID playerId
) {
    return new PlayerStatistics(totalGamesPlayed, totalWins, totalFriends, bestRecordTime, playerId);
}

    private static void validatePlayerId(UUID playerId) {
        if (playerId == null) {
            throw new InvalidAchievementException("Player ID cannot be null in statistics");
        }
    }

    private static void validateStatistics(
            Long totalGamesPlayed,
            Long totalWins,
            Long totalFriends,
            Long bestRecordTime
    ) {
        if (totalGamesPlayed != null && totalGamesPlayed < 0) {
            throw new InvalidAchievementException(
                    "Total games played cannot be negative, received: " + totalGamesPlayed
            );
        }

        if (totalWins != null && totalWins < 0) {
            throw new InvalidAchievementException(
                    "Total wins cannot be negative, received: " + totalWins
            );
        }

        if (totalFriends != null && totalFriends < 0) {
            throw new InvalidAchievementException(
                    "Total friends cannot be negative, received: " + totalFriends
            );
        }

        if (bestRecordTime != null && bestRecordTime < 0) {
            throw new InvalidAchievementException(
                    "Best record time cannot be negative, received: " + bestRecordTime
            );
        }
    }

    public boolean hasStatistic(String statisticType) {
        return switch (statisticType) {
            case "GAMES_PLAYED" -> totalGamesPlayed != null;
            case "WINS" -> totalWins != null;
            case "FRIENDS" -> totalFriends != null;
            case "BEST_TIME" -> bestRecordTime != null;
            default -> false;
        };
    }

    public long getStatisticValue(String statisticType) {
        return switch (statisticType) {
            case "GAMES_PLAYED" -> totalGamesPlayed != null ? totalGamesPlayed : 0L;
            case "WINS" -> totalWins != null ? totalWins : 0L;
            case "FRIENDS" -> totalFriends != null ? totalFriends : 0L;
            case "BEST_TIME" -> bestRecordTime != null ? bestRecordTime : 0L;
            default -> 0L;
        };
    }
}
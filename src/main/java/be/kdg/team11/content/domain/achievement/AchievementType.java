package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementTypeException;

/**
 * Enum representing different types of achievements a player can earn.
 * Each type defines specific criteria for achievement completion based on player statistics.
 *
 * Supports:
 * - PLAY_COUNT: Unlocked after playing N games
 * - WIN_COUNT: Unlocked after winning N games
 * - FRIEND_COUNT: Unlocked after making N friends
 * - RECORD_TIME: Unlocked when best time is under N milliseconds
 */
public enum AchievementType {
    /**
     * Achievement based on total games played.
     * Player must play at least 'requiredValue' games to unlock.
     */
    PLAY_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalGamesPlayed();
            return actual != null && actual >= requiredValue;
        }
    },
    /**
     * Achievement based on total wins.
     * Player must win at least 'requiredValue' games to unlock.
     */
    WIN_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalWins();
            return actual != null && actual >= requiredValue;
        }
    },
    /**
     * Achievement based on friend count.
     * Player must have at least 'requiredValue' friends to unlock.
     */
    FRIEND_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalFriends();
            return actual != null && actual >= requiredValue;
        }
    },
    /**
     * Achievement based on best record time.
     * Player must achieve a time LESS THAN OR EQUAL to 'requiredValue' milliseconds.
     * Lower times are better (this is a "speedrun" style achievement).
     */
    RECORD_TIME {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.bestRecordTime();
            Long required = requiredValue;
            return actual != null && actual.compareTo(required) <= 0;
        }
    };

    /**
     * Determines if an achievement is met based on player statistics.
     * Each type implements its own criteria for achievement completion.
     */
    public abstract boolean isMetBy(long requiredValue, PlayerStatistics stats);

    /**
     * Validates that required value is non-negative.
     */
    private static void validateRequiredValue(long requiredValue) {
        if (requiredValue < 0) {
            throw new InvalidAchievementTypeException(
                    "Required value cannot be negative, received: " + requiredValue
            );
        }
    }
}
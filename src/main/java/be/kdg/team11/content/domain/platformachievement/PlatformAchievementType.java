package be.kdg.team11.content.domain.platformachievement;
/**
 * Enum representing different types of gameAchievements a player can earn.
 * Each type defines specific criteria for achievement completion.
 * <p>
 * Supports:
 * - PLAY_COUNT: Unlocked after playing N games
 * - WIN_COUNT: Unlocked after winning N games
 * - FRIEND_COUNT: Unlocked after making N friends
 * - RECORD_TIME: Unlocked when best time is under N milliseconds
 */
public enum PlatformAchievementType {
    /**
     * Achievement based on total games played.
     * Player must play at least 'requiredValue' games to unlock.
     */
    PLAY_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, long actualValue) {
            return actualValue >= requiredValue;
        }
    },
    /**
     * Achievement based on total wins.
     * Player must win at least 'requiredValue' games to unlock.
     */
    WIN_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, long actualValue) {
            return actualValue >= requiredValue;
        }
    },
    /**
     * Achievement based on friend count.
     * Player must have at least 'requiredValue' friends to unlock.
     */
    FRIEND_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, long actualValue) {
            return actualValue >= requiredValue;
        }
    },
    /**
     * Achievement based on best record time.
     * Player must achieve a time LESS THAN OR EQUAL to 'requiredValue' milliseconds.
     * Lower times are better (this is a "speedrun" style achievement).
     */
    RECORD_TIME {
        @Override
        public boolean isMetBy(long requiredValue, long actualValue) {
            return actualValue <= requiredValue;
        }
    };

    /**
     * Determines if an achievement is met based on player statistics.
     * Each type implements its own criteria for achievement completion.
     */
    public abstract boolean isMetBy(long requiredValue, long actualValue);
}
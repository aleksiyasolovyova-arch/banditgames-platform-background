package be.kdg.team11.content.domain.achievement;

public enum AchievementType {
    PLAY_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalGamesPlayed();
            return actual != null && actual >= requiredValue;
        }
    },
    WIN_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalWins();
            return actual != null && actual >= requiredValue;
        }
    },
    FRIEND_COUNT {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.totalFriends();
            return actual != null && actual >= requiredValue;
        }
    },
    RECORD_TIME {
        @Override
        public boolean isMetBy(long requiredValue, PlayerStatistics stats) {
            Long actual = stats.bestRecordTime();
            Long required = requiredValue;
            return actual != null && actual.compareTo(required) <= 0;
        }
    };

    public abstract boolean isMetBy(long requiredValue, PlayerStatistics stats);
}
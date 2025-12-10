package team11.platform_backend.game.domain.achievement;

import team11.platform_backend.sharedkernel.events.GameCompletedEvent;

public record CountThreshold(long value, AchievementType type) implements Threshold {
    public CountThreshold {
        if (value <= 0) throw new IllegalArgumentException("Count must be > 0");
    }
    @Override
    public boolean isMetBy(GameCompletedEvent e) {
        long actual = switch (type) {
            case PLAY_COUNT   -> e.totalGamesPlayed();
            case WIN_COUNT    -> e.totalWins();
            case FRIEND_COUNT -> e.totalFriends();
            default -> throw new IllegalStateException("Unsupported type for CountThreshold: " + type);
        };
        return actual >= value;
    }
}

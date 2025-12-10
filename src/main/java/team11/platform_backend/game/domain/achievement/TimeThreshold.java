package team11.platform_backend.game.domain.achievement;

import team11.platform_backend.sharedkernel.events.GameCompletedEvent;

import java.time.Duration;

public record TimeThreshold(Duration value) implements Threshold {
    public TimeThreshold {
        if (value.isNegative() || value.isZero())
            throw new IllegalArgumentException("Duration must be > 0");
    }
    @Override
    public boolean isMetBy(GameCompletedEvent e) {
        Duration actual = e.bestRecordTime();
        return actual.compareTo(value) <= 0; // “faster or equal”
    }
}

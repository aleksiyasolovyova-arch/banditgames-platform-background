package team11.platform_backend.game.domain.achievement;

import java.math.BigDecimal;

public record AchievementThreshold(
        AchievementType achievementType,
        BigDecimal threshold
) {
    public AchievementThreshold {
        if (threshold == null || !isThresholdValid(threshold)) {
            throw new IllegalArgumentException("Threshold cannot be null");
        }
    }

    private boolean isThresholdValid(BigDecimal threshold) {
        return threshold.compareTo(BigDecimal.ZERO) > 0;

    }
}

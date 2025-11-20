package team11.platform_backend.game.domain.achievement;

import java.math.BigDecimal;

//TODO think of a better type for threshold
public record AchievementThreshold(
        AchievementType achievementType,
        BigDecimal threshold
) {
    public AchievementThreshold(AchievementType achievementType, BigDecimal threshold) {
        if (threshold == null || !isThresholdValid(threshold)) {
            throw new IllegalArgumentException("Threshold cannot be null");
        }
        this.achievementType = achievementType;
        this.threshold = threshold;

    }

    private boolean isThresholdValid(BigDecimal threshold) {
        return threshold.compareTo(BigDecimal.ZERO) > 0;

    }
}

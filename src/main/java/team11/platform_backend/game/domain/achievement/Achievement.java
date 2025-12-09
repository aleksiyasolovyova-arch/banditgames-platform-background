package team11.platform_backend.game.domain.achievement;

import team11.platform_backend.game.domain.Url;
import team11.platform_backend.sharedkernel.events.GameCompletedEvent;

import java.math.BigDecimal;

// Aggregate
// Global achievement
public class Achievement {
    private final AchievementId achievementId;
    private final String name;
    private final String description;
    private final Url pictureUrl;
    private final Threshold threshold;

    public Achievement(AchievementId achievementId, String name, String description, Url pictureUrl, Threshold threshold) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.threshold = threshold;
    }
    public Achievement(String name, String description, Url pictureUrl, Threshold threshold) {
        this.achievementId = AchievementId.create();
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.threshold = threshold;
    }

    public boolean isThresholdMet(GameCompletedEvent event) {
        return threshold.isMetBy(event);
    }

    public AchievementId getAchievementId() {
        return achievementId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Url getPictureUrl() {
        return pictureUrl;
    }

    public Threshold getThreshold() {
        return threshold;
    }
}

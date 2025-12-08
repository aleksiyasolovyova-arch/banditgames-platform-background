package team11.platform_backend.game.domain.achievement;

import team11.platform_backend.game.domain.Url;

import java.math.BigDecimal;

//Aggregate
// Global achievement
public class Achievement {
    private final AchievementId achievementId;
    private final String name;
    private final String description;
    private final Url pictureUrl;
    private final AchievementThreshold achievementThreshold;

    public Achievement(AchievementId achievementId, String name, String description, Url pictureUrl, AchievementThreshold achievementThreshold) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.achievementThreshold = achievementThreshold;
    }

    // for creating
    public Achievement(String name ,String description, Url pictureUrl, AchievementThreshold achievementThreshold) {
        this.achievementId = AchievementId.create();
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.achievementThreshold = achievementThreshold;
    }

    public boolean isThresholdMet(BigDecimal score){
        if(achievementThreshold.achievementType().equals(AchievementType.RECORD_TIME)){
            return score.compareTo(achievementThreshold.threshold()) <= 0;
        }
        return score.compareTo(achievementThreshold.threshold()) >= 0;
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

    public AchievementThreshold getAchievementThreshold() {
        return achievementThreshold;
    }
}

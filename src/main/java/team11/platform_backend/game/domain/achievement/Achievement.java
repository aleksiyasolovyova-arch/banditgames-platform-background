package team11.platform_backend.game.domain.achievement;

import team11.platform_backend.game.domain.AchievementId;
import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.math.BigDecimal;

//Aggregate
public class Achievement {
    private final AchievementId achievementId;
    private final String achievementName;
    private final String achievementDescription;
    private final Url pictureUrl;
    private final AchievementThreshold achievementThreshold;

    // for getting (get methods)
    public Achievement(AchievementId achievementId, String achievementName, String achievementDescription, Url pictureUrl, AchievementThreshold achievementThreshold) {
        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.pictureUrl = pictureUrl;
        this.achievementThreshold = achievementThreshold;
    }

    // for creating (post methods)
    public Achievement(String achievementName, String achievementDescription, Url pictureUrl, AchievementThreshold achievementThreshold) {
        this.achievementId = AchievementId.createAchievementId();
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.pictureUrl = pictureUrl;
        this.achievementThreshold = achievementThreshold;
    }

    public AchievementId getAchievementId() {
        return achievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public Url getPictureUrl() {
        return pictureUrl;
    }

    public AchievementThreshold getAchievementThreshold() {
        return achievementThreshold;
    }

    public boolean isThresholdMet(BigDecimal score){
        if(achievementThreshold.achievementType().equals(AchievementType.RECORD_TIME)){
            return score.compareTo(achievementThreshold.threshold()) <= 0;
        }
        return score.compareTo(achievementThreshold.threshold()) >= 0;

    }
}

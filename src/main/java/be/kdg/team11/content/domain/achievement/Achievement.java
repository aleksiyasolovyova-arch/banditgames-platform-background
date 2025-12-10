package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.Url;

// Aggregate
// Global achievement
public class Achievement {
    private final AchievementId achievementId;
    private final String name;
    private final String description;
    private final Url pictureUrl;
    private final AchievementType type;
    private final long requiredValue;

    public Achievement(AchievementId achievementId, String name, String description, Url pictureUrl, AchievementType type, long requiredValue) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.requiredValue = requiredValue;
    }
    public Achievement(String name, String description, Url pictureUrl, AchievementType type, long requiredValue) {
        this.achievementId = AchievementId.create();
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.requiredValue = requiredValue;
    }

    public boolean isAchievementMet(PlayerStatistics statistics) {
        return type.isMetBy(requiredValue, statistics);
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

    public AchievementType getType() {
        return type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }
}

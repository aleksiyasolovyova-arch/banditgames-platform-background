package team11.platform_backend.game.adapter.out.jpa;

import jakarta.persistence.*;
import team11.platform_backend.game.domain.achievement.AchievementType;
import java.util.UUID;

@Entity
@Table(name = "achievements", schema = "game_schema")
public class AchievementJpaEntity {

    @Id
    private UUID achievementId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType achievementType;

    // For CountThreshold: stores the count value
    @Column(name = "threshold_value")
    private Long thresholdCountValue;

    // For TimeThreshold: stores duration in seconds
    @Column(name = "threshold_duration_seconds")
    private Long thresholdDurationSeconds;

    public AchievementJpaEntity() {}

    public UUID getAchievementId() {
        return achievementId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public Long getThresholdCountValue() {
        return thresholdCountValue;
    }

    public Long getThresholdDurationSeconds() {
        return thresholdDurationSeconds;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }

    public void setThresholdCountValue(Long thresholdCountValue) {
        this.thresholdCountValue = thresholdCountValue;
    }

    public void setThresholdDurationSeconds(Long thresholdDurationSeconds) {
        this.thresholdDurationSeconds = thresholdDurationSeconds;
    }
}
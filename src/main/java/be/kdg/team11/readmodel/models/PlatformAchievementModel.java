package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "achievement", schema = "read_model_schema")
public class PlatformAchievementModel {
    @Id
    private UUID achievementId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private long requiredValue;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public PlatformAchievementModel() {}

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

    public String getType() {
        return type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setRequiredValue(long requiredValue) {
        this.requiredValue = requiredValue;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

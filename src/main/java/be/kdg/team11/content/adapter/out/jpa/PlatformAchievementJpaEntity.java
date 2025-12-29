package be.kdg.team11.content.adapter.out.jpa;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievementType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "platform_achievements", schema = "content_schema")
public class PlatformAchievementJpaEntity {

    @Id
    private UUID platformAchievementId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlatformAchievementType type;

    @Column(nullable = false)
    private long requiredValue;

    public PlatformAchievementJpaEntity() {
    }

    public UUID getPlatformAchievementId() {
        return platformAchievementId;
    }

    public void setPlatformAchievementId(UUID platformAchievementId) {
        this.platformAchievementId = platformAchievementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public PlatformAchievementType getType() {
        return type;
    }

    public void setType(PlatformAchievementType type) {
        this.type = type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }

    public void setRequiredValue(long requiredValue) {
        this.requiredValue = requiredValue;
    }
}
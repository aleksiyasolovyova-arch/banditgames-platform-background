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
    private AchievementType type;

    @Column(nullable = false)
    private long requiredValue;

    public AchievementJpaEntity() {}

    public UUID getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
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

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }

    public void setRequiredValue(long requiredValue) {
        this.requiredValue = requiredValue;
    }
}
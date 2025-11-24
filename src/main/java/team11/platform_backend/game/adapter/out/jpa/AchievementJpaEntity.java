package team11.platform_backend.game.adapter.out.jpa;

import jakarta.persistence.*;
import team11.platform_backend.game.domain.achievement.AchievementType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "achievements")
public class AchievementJpaEntity {

    @Id
    private UUID achievementId;

    @Column(nullable = false)
    private UUID gameId; // Foreign key to Game

    @Column(nullable = false, length = 100)
    private String achievementName;

    @Column(nullable = false, length = 255)
    private String achievementDescription;

    @Column(nullable = false)
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType achievementType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal threshold;

    // Constructors
    public AchievementJpaEntity() {}

    // Getters and Setters
    public UUID getAchievementId() { return achievementId; }
    public void setAchievementId(UUID achievementId) { this.achievementId = achievementId; }

    public UUID getGameId() { return gameId; }
    public void setGameId(UUID gameId) { this.gameId = gameId; }

    public String getAchievementName() { return achievementName; }
    public void setAchievementName(String achievementName) { this.achievementName = achievementName; }

    public String getAchievementDescription() { return achievementDescription; }
    public void setAchievementDescription(String achievementDescription) { this.achievementDescription = achievementDescription; }

    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public AchievementType getAchievementType() { return achievementType; }
    public void setAchievementType(AchievementType achievementType) { this.achievementType = achievementType; }

    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }
}

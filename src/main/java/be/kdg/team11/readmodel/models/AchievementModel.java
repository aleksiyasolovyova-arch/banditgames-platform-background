package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "achievement", schema = "read_model_schema")
public class AchievementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "achievement_id")
    private UUID achievementIdPK;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "achievement_type", nullable = false)
    private AchievementModelType type;

    @Column
    private UUID platformAchievementId;

    @Column(name = "name")
    private String name;

    @Column(name = "picture_url")
    private String pictureUrl;

    // Value required to unlock achievement (null for game achievements)
    @Column(name = "required_value")
    private Long requiredValue;

    // Game reference (null for platform achievements)
    @Column(name = "game_id")
    private UUID gameId;

    // Game achievement code (null for platform achievements)
    @Column(name = "achievement_code")
    private String achievementCode;

    // Denormalized game name for easier querying
    @Column(name = "game_name")
    private String gameName;

    // Timestamps
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructors
    public AchievementModel() {}



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

    public AchievementModelType getType() {
        return type;
    }

    public void setType(AchievementModelType type) {
        this.type = type;
    }

    public Long getRequiredValue() {
        return requiredValue;
    }

    public void setRequiredValue(Long requiredValue) {
        this.requiredValue = requiredValue;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getAchievementCode() {
        return achievementCode;
    }

    public void setAchievementCode(String achievementCode) {
        this.achievementCode = achievementCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

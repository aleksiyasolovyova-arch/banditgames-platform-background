package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "read_model_schema")
public class AchievementModel {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID playerId;

    @Column
    private UUID achievementId;

    @Column
    private UUID gameId;

    @Column
    private String gameAchievementCode;

    @Column(nullable = false)
    private LocalDateTime unlockedAt;

    public AchievementModel() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public LocalDateTime getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public String getGameAchievementCode() {
        return gameAchievementCode;
    }

    public void setGameAchievementCode(String gameAchievementCode) {
        this.gameAchievementCode = gameAchievementCode;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}

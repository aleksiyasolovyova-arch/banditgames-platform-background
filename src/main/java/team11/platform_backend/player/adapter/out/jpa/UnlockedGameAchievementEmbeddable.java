package team11.platform_backend.player.adapter.out.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public class UnlockedGameAchievementEmbeddable {

    @Column(nullable = false)
    private UUID gameId;

    @Column(nullable = false, length = 100)
    private String achievementCode;

    @Column(nullable = false)
    private LocalDateTime unlockedAt;

    public UnlockedGameAchievementEmbeddable() {}

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

    public LocalDateTime getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
}
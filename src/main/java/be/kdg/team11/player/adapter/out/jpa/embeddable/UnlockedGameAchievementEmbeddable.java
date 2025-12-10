package be.kdg.team11.player.adapter.out.jpa.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public class UnlockedGameAchievementEmbeddable {

    @Column(nullable = false)
    private UUID gameId;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false)
    private LocalDateTime unlockedAt;

    public UnlockedGameAchievementEmbeddable() {}

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
}
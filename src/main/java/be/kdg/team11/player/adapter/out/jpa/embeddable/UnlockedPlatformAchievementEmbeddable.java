package be.kdg.team11.player.adapter.out.jpa.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public class UnlockedPlatformAchievementEmbeddable {

    @Column(nullable = false)
    private UUID achievementId;

    @Column(nullable = false)
    private LocalDateTime unlockedAt;

    public UnlockedPlatformAchievementEmbeddable() {
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
}
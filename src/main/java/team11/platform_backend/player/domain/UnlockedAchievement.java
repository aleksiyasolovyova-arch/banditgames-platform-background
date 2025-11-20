package team11.platform_backend.player.domain;
import java.time.LocalDateTime;

public class UnlockedAchievement {
    private final AchievementId achievementId;
    private final LocalDateTime unlockAt;

    public UnlockedAchievement(AchievementId achievementId, LocalDateTime unlockAt) {
        this.achievementId = achievementId;
        this.unlockAt = unlockAt;
    }

    public UnlockedAchievement(AchievementId achievementId) {
        this.achievementId = achievementId;
        this.unlockAt = LocalDateTime.now();
    }

    public AchievementId getAchievementId() {
        return achievementId;
    }

    public LocalDateTime getUnlockAt() {
        return unlockAt;
    }
}

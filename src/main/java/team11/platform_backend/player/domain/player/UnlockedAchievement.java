package team11.platform_backend.player.domain.player;
import java.time.LocalDateTime;

public record UnlockedAchievement(
        AchievementId achievementId,
        LocalDateTime unlockedAt
) {}

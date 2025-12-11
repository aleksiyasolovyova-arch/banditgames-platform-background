package be.kdg.team11.player.domain.player;


import java.time.LocalDateTime;

public record UnlockedPlatformAchievement(
        AchievementId achievementId,
        LocalDateTime unlockedAt
) {
}

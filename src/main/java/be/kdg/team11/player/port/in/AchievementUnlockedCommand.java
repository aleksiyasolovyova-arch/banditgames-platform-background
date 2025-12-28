package be.kdg.team11.player.port.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record AchievementUnlockedCommand(
        UUID playerId,
        UUID achievementId,
        LocalDateTime unlockedAt
) {
}

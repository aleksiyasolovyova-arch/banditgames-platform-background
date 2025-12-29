package be.kdg.team11.player.port.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlatformAchievementUnlockedCommand(
        UUID playerId,
        UUID achievementId,
        LocalDateTime unlockedAt
) {
}

package be.kdg.team11.player.adapter.in.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record PlayerDto(
        UUID playerId,
        String username,
        String pictureUrl,
        LocalDate joinedDate,
        Set<UnlockedAchievementDto> unlockedAchievements,
        Set<UnlockedGameAchievementDto> unlockedGameAchievements,
        UUID favoriteGameId
) {

    public record UnlockedAchievementDto(
            UUID achievementId,
            LocalDateTime unlockedAt
    ) {
    }

    public record UnlockedGameAchievementDto(
            UUID gameId,
            String code,
            LocalDateTime unlockedAt
    ) {
    }
}

package be.kdg.team11.readmodel.controller.dto.game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//todo embed dtos add game picture
public record PlayerProfileDto(
        UUID playerId,
        String username,
        String pictureUrl,
        LocalDate joinedDate,
        LocalDateTime lastActive,
        UUID favouriteGameId,
        String favouriteGameName,
        PlayerStatisticsDto statistics,
        List<PlayerHistoryDto> games
) {
}

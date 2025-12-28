package be.kdg.team11.readmodel.controller.dto.game;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlayerHistoryDto(
        UUID lobbyId,
        UUID gameId,
        String gameName,
        UUID opponentId,
        String opponentUsername,
        String opponentPictureUrl,
        String result,
        Integer durationMinutes,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {
}

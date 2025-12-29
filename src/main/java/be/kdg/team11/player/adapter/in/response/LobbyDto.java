package be.kdg.team11.player.adapter.in.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyDto(
        UUID lobbyId,
        UUID gameReference,
        String result,
        UUID player1Id,
        String participationStatusPlayer1,
        UUID player2Id,
        String participationStatusPlayer2,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String lobbyResult
) {
}

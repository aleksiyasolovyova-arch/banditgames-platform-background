package be.kdg.team11.player.adapter.in.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyDto(
        UUID lobbyId,
        UUID gameReference,
        UUID player1Id,
        UUID player2Id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String lobbyResult,
        String link
) {
}

package be.kdg.team11.content.port.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyEndedWithDrawCommand(
        UUID lobbyId,
        UUID player1Id,
        UUID player2Id,
        long time,
        LocalDateTime eventPit
) {
}

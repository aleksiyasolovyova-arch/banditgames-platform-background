package be.kdg.team11.player.port.in;

import java.util.UUID;

public record RejectLobbyCommand(
        UUID lobbyId,
        UUID playerId
) {
}

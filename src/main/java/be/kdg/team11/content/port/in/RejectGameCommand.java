package be.kdg.team11.content.port.in;

import java.util.UUID;

public record RejectGameCommand(
        UUID gameId
) {
    public RejectGameCommand {
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }
    }
}

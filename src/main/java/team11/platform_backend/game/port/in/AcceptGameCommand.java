package team11.platform_backend.game.port.in;

import java.util.UUID;

public record AcceptGameCommand(
        UUID gameId
) {
    public AcceptGameCommand {
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }
    }
}

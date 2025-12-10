package be.kdg.team11.content.port.in;

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

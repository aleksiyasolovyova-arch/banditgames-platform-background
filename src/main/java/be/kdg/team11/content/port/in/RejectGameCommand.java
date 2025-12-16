package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;

import java.util.UUID;

public record RejectGameCommand(
        UUID gameId
) {
    public RejectGameCommand {
        if (gameId == null) {
            throw new InvalidGameDataException("Game ID cannot be null");
        }
    }
}

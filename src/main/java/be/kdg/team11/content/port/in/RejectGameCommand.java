package be.kdg.team11.content.port.in;

import org.springframework.util.Assert;

import java.util.UUID;

public record RejectGameCommand(
        UUID gameId
) {
    public RejectGameCommand {
        Assert.notNull(gameId, "Game ID cannot be null");
    }
}

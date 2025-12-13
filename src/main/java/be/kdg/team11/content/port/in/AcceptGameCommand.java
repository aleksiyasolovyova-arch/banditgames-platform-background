package be.kdg.team11.content.port.in;

import org.springframework.util.Assert;

import java.util.UUID;

public record AcceptGameCommand(
        UUID gameId
) {
    public AcceptGameCommand {
        Assert.notNull(gameId, "Game ID cannot be null");
    }
}

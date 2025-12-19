package be.kdg.team11.player.port.in;

import java.util.UUID;

public record BuyGameCommand(
        UUID playerId,
        UUID gameId
) {
}

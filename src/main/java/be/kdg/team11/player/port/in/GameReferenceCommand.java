package be.kdg.team11.player.port.in;

import java.util.UUID;

public record GameReferenceCommand(
        UUID gameId,
        String gameUrl
) {}

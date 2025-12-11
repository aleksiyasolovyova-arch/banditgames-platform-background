package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.PlayerId;

public record CreateNewPlayerCommand(
        PlayerId playerId
) {
    public CreateNewPlayerCommand {
        if (playerId == null) {
            throw new IllegalArgumentException("Player ID cannot be null");
        }
    }
}

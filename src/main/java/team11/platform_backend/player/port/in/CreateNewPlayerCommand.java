package team11.platform_backend.player.port.in;

import team11.platform_backend.player.domain.player.PlayerId;

public record CreateNewPlayerCommand(
        PlayerId playerId
) {
    public CreateNewPlayerCommand {
        if (playerId == null) {
            throw new IllegalArgumentException("Player ID cannot be null");
        }
    }
}

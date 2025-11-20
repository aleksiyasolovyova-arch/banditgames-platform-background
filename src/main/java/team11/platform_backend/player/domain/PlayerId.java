package team11.platform_backend.player.domain;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    public static PlayerId createPlayerId() {
        return new PlayerId(UUID.randomUUID());
    }
}

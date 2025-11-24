package team11.platform_backend.gamesession.domain;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    public static PlayerId createPlayerId() {
        return new PlayerId(UUID.randomUUID());
    }
}

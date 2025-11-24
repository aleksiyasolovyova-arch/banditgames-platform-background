package team11.platform_backend.gamesession.domain;

import java.util.UUID;

public record GameId(
        UUID gameId
) {
    public static GameId createGameId() {
        return new GameId(UUID.randomUUID());
    }
}

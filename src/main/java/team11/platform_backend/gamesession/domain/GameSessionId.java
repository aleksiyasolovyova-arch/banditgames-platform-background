package team11.platform_backend.gamesession.domain;

import java.util.UUID;

public record GameSessionId(
        UUID gameSessionId
) {
    public static GameSessionId createGameSessionId() {
        return new GameSessionId(UUID.randomUUID());
    }

}

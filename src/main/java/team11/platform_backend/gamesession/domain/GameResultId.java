package team11.platform_backend.gamesession.domain;

import java.util.UUID;

public record GameResultId(
        UUID gameSessionId
) {
    public static GameResultId createGameSessionId() {
        return new GameResultId(UUID.randomUUID());
    }

}

package team11.platform_backend.player.domain.gamesession;

import java.util.UUID;

public record GameSessionId(
        UUID gameSessionId
) {
    public static GameSessionId createGameSessionId() {
        return new GameSessionId(UUID.randomUUID());
    }

}

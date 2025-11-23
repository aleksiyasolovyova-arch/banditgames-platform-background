package team11.platform_backend.player.domain.gamesession;

import java.util.UUID;

public record GameStateId(
        UUID gameStateId
) {
    public static GameStateId createGameStateId() {
        return new GameStateId(UUID.randomUUID());
    }
}

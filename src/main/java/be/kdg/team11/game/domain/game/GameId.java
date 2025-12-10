package be.kdg.team11.game.domain.game;

import java.util.UUID;

public record GameId(
        UUID gameId
) {
    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }
}

package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;

import java.util.UUID;

public record GameId(
        UUID gameId
) {
    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }

    public static GameId of(UUID uuid) {
        return new GameId(uuid);
    }

    public static GameNotFoundException notFound(GameId gameId) {
        return new GameNotFoundException(
                String.format("Game not found with ID: %s", gameId.gameId())
        );
    }
}

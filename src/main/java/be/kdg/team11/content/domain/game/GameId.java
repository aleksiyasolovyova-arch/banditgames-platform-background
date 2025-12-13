package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;

import java.util.UUID;

public record GameId(
        UUID gameId
) {
    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }

    public static GameId of(UUID uuid) {
        if (uuid == null) {
            throw new InvalidGameDataException("Game ID UUID cannot be null");
        }
        return new GameId(uuid);
    }

    public static InvalidGameDataException notFound(GameId gameId) {
        return new InvalidGameDataException(
                String.format("Game not found with ID: %s", gameId.gameId())
        );
    }
}

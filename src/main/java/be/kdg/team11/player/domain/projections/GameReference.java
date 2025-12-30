package be.kdg.team11.player.domain.projections;

import be.kdg.team11.player.domain.projections.exceptions.GameReferenceAlreadyExistsException;
import be.kdg.team11.player.domain.projections.exceptions.GameReferenceNotFoundException;

import java.util.UUID;

public record GameReference(
        UUID gameId,
        String gameUrl
) {
    public static GameReference of(UUID gameId, String gameUrl) {
        return new GameReference(gameId, gameUrl);
    }

    public static GameReferenceAlreadyExistsException alreadyExists(UUID gameId) {
        return new GameReferenceAlreadyExistsException(
                String.format("Cannot add the same gameId twice: %s", gameId)
        );
    }

    public static GameReferenceNotFoundException notFound(UUID gameId) {
        return new GameReferenceNotFoundException(
                String.format("Game not found with ID: %s", gameId)
        );

    }
}
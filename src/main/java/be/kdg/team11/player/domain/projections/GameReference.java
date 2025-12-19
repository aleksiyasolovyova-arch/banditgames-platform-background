package be.kdg.team11.player.domain.projections;

import java.util.UUID;

public record GameReference(
        UUID gameId
) {
    public static GameReference of (UUID gameId){
        return new GameReference(gameId);
    }
    public static GameNotFoundException notFound(UUID gameId){
        return new GameNotFoundException(
                String.format("Game not found with ID: %s", gameId)
        );

}
}
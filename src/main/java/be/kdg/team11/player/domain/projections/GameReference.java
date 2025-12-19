package be.kdg.team11.player.domain.projections;

import java.util.UUID;

public record GameReference(
        UUID gameId
) {
    public static GameReference of (UUID gameId){
        return new GameReference(gameId);
    }
}
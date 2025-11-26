package team11.platform_backend.gamelobby.domain.projections;

import java.util.ArrayList;
import java.util.List;

// Projection of Player in Player BC
public class PlayerProjection {
    private final PlayerId playerId;
    private final List<GameId> ownedGames = new ArrayList<>();

    public PlayerProjection(PlayerId playerId,  List<GameId> ownedGames) {
        this.playerId = playerId;
        this.ownedGames.addAll(ownedGames);
    }

    public PlayerId getPlayerId() {
        return playerId;
    }
    public List<GameId> getOwnedGames() {
        return ownedGames;
    }
}
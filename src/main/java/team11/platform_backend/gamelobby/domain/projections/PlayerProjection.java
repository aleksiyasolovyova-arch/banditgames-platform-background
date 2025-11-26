package team11.platform_backend.gamelobby.domain.projections;

// Projection of Player in Player BC
public class PlayerProjection {
    private final PlayerId playerId;

    public PlayerProjection(PlayerId playerId) {
        this.playerId = playerId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }
}
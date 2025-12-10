package be.kdg.team11.player.domain.projections;

public class GameProjection {
    private final GameId gameId;
    public GameProjection(GameId gameId) {
        this.gameId = gameId;
    }
    public GameId getGameId() {
        return gameId;
    }
}

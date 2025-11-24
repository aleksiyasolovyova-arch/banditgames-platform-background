package team11.platform_backend.gamesession.domain;

public class GameLobby {
    private final GameLobbyId gameLobbyId;
    private final GameId gameId;
    private final PlayerId playerId1;
    private final PlayerId playerId2;
    private boolean player1Accepted;
    private boolean player2Accepted;

    // for creation
    public GameLobby(GameLobbyId gameLobbyId, GameId gameId, PlayerId playerId1, PlayerId playerId2) {
        this.gameLobbyId = gameLobbyId;
        this.gameId = gameId;
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
    }
}

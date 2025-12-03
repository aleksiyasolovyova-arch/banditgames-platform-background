package team11.platform_backend.player.domain.gamelobby;


import team11.platform_backend.player.domain.player.PlayerId;
import team11.platform_backend.player.domain.projections.GameId;

import java.time.LocalDateTime;

public class GameLobby {
    private final GameLobbyId gameLobbyId;
    private final GameId gameId;
    private final PlayerId playerId1;
    private final PlayerId playerId2;
    private boolean player1Accepted;
    private boolean player2Accepted;
    private GameResult gameResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // For loading
    public GameLobby(GameLobbyId gameLobbyId, GameId gameId, PlayerId playerId1, PlayerId playerId2, LocalDateTime endTime, boolean player1Accepted, boolean player2Accepted, GameResult gameResult, LocalDateTime startTime) {
        this.gameLobbyId = gameLobbyId;
        this.gameId = gameId;
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
        this.player1Accepted = player1Accepted;
        this.player2Accepted = player2Accepted;
        this.gameResult = gameResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // For creating
    // It is private because I want to force other developers to use the static methods of creating a new GameLobby
    private GameLobby(GameId gameId, PlayerId playerId1, PlayerId playerId2, boolean player1Accepted, boolean player2Accepted) {
        this.gameLobbyId = GameLobbyId.createGameLobbyId();
        this.gameId = gameId;
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
        this.player1Accepted = player1Accepted;
        this.player2Accepted = player2Accepted;
        this.gameResult = GameResult.PENDING;
    }


    public static GameLobby createGameLobbyForStrangers(GameId gameId, PlayerId playerId1, PlayerId playerId2) {
        return new GameLobby(
                gameId,
                playerId1,
                playerId2,
                false,
                false
        );
    }

    public static GameLobby createGameLobbyForFriends(GameId gameId, PlayerId playerId1, PlayerId playerId2) {
        return new GameLobby(
                gameId,
                playerId1,
                playerId2,
                true,
                false
        );
    }

    public static GameLobby createGameLobbyForAI(GameId gameId, PlayerId playerId1) {
        GameLobby gameLobby = new GameLobby(
                gameId,
                playerId1,
                PlayerId.ai(),
                true,
                true
        );
        gameLobby.startGame();
        return gameLobby;
    }

    public void acceptByPlayer(PlayerId playerId) {
        if (playerId1.equals(playerId)) {
            if (!player1Accepted) {
                this.player1Accepted = true;
            }
        } else if (playerId2.equals(playerId)) {
            if (!player2Accepted) {
                this.player2Accepted = true;
            }
        } else {
            throw new IllegalArgumentException("Invalid player id provided");
        }
        if (player1Accepted && player2Accepted && gameResult.equals(GameResult.PENDING)) {
            startGame();
        }
    }

    public void cancelGame(){
        if (gameResult.equals(GameResult.PENDING) || gameResult.equals(GameResult.STARTED)) {
            this.gameResult = GameResult.CANCELED;
        } else {
            throw new IllegalArgumentException("Cannot cancel an already finished game!");
        }
    }

    public void startGame() {
        if (gameResult.equals(GameResult.PENDING) && player1Accepted && player2Accepted) {
            this.gameResult = GameResult.STARTED;
            this.startTime = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Cannot start unless both players agree to start a pending game!");
        }
    }

    public void endGameWithWinner(PlayerId winnerId) {
        if (!gameResult.equals(GameResult.STARTED)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }
        if (playerId1.equals(winnerId)) {
            this.gameResult = GameResult.PLAYER_1_WON;
        } else if (playerId2.equals(winnerId)) {
            this.gameResult = GameResult.PLAYER_2_WON;
        } else {
            throw new IllegalArgumentException("Invalid player id provided");
        }
        this.endTime = LocalDateTime.now();
    }

    public void endGameWithDraw() {
        if (!gameResult.equals(GameResult.STARTED)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }
        this.gameResult = GameResult.DRAW;
        this.endTime = LocalDateTime.now();
    }

    public boolean isAgainstAi(){
        return playerId2.isAI();
    }

    public GameLobbyId getGameLobbyId() {
        return gameLobbyId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public PlayerId getPlayerId1() {
        return playerId1;
    }

    public PlayerId getPlayerId2() {
        return playerId2;
    }

    public boolean isPlayer1Accepted() {
        return player1Accepted;
    }

    public boolean isPlayer2Accepted() {
        return player2Accepted;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}

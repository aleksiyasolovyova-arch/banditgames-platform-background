package be.kdg.team11.player.domain.gamelobby;


import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

public class GameLobby {
    private final GameLobbyId gameLobbyId;
    private final GameReference gameReference;
    private final Pair<PlayerId, PlayerId> playerIdPair;
    private Boolean player1Accepted;
    private Boolean player2Accepted;
    private GameLobbyStatus gameLobbyStatus;
    private GameLobbyResult gameLobbyResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public GameLobby(GameLobbyId gameLobbyId, GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair, Boolean player1Accepted, Boolean player2Accepted, GameLobbyStatus gameLobbyStatus, GameLobbyResult gameLobbyResult, LocalDateTime startTime, LocalDateTime endTime) {
        this.gameLobbyId = gameLobbyId;
        this.gameReference = gameReference;
        this.playerIdPair = playerIdPair;
        this.player1Accepted = player1Accepted;
        this.player2Accepted = player2Accepted;
        this.gameLobbyStatus = gameLobbyStatus;
        this.gameLobbyResult = gameLobbyResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public static GameLobby createGameLobbyForStrangers(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {
        return new GameLobby(
                GameLobbyId.create(),
                gameReference,
                playerIdPair,
                false, false,
                GameLobbyStatus.PENDING,
                GameLobbyResult.NO_RESULT,
                LocalDateTime.now(),
                null
        );
    }

    public static GameLobby createGameLobbyForFriends(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {
        return new GameLobby(
                GameLobbyId.create(),
                gameReference,
                playerIdPair,
                true, false,
                GameLobbyStatus.PENDING,
                GameLobbyResult.NO_RESULT,
                LocalDateTime.now(),
                null
        );
    }

    public static GameLobby createGameLobbyForAI(GameReference gameReference, PlayerId playerId) {
        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(playerId, PlayerId.ai());
        GameLobby gameLobby = new GameLobby(
                GameLobbyId.create(),
                gameReference,
                playerIdPair,
                true, true,
                GameLobbyStatus.PENDING,
                GameLobbyResult.NO_RESULT,
                LocalDateTime.now(),
                null
        );
        gameLobby.startGame();
        return gameLobby;
    }

    public void acceptByPlayer(PlayerId playerId) {
        if (playerIdPair.getFirst().equals(playerId)) {
            if (!player1Accepted) {
                this.player1Accepted = true;
            }
        } else if (playerIdPair.getSecond().equals(playerId)) {
            if (!player2Accepted) {
                this.player2Accepted = true;
            }
        } else {
            throw new IllegalArgumentException("Invalid player id provided");
        }
        if (player1Accepted && player2Accepted && gameLobbyStatus.equals(GameLobbyStatus.PENDING)) {
            startGame();
        }
    }

    public void cancelGame() {
        if (gameLobbyStatus.equals(GameLobbyStatus.PENDING) || gameLobbyStatus.equals(GameLobbyStatus.STARTED)) {
            this.gameLobbyStatus = GameLobbyStatus.CANCELED;
        } else {
            throw new IllegalArgumentException("Cannot cancel an already finished game!");
        }
    }

    public void startGame() {
        if (gameLobbyStatus.equals(GameLobbyStatus.PENDING) && player1Accepted && player2Accepted) {
            this.gameLobbyStatus = GameLobbyStatus.STARTED;
            this.startTime = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Cannot start unless both players agree to start a pending game!");
        }
    }

    public void endGameWithWinner(PlayerId winnerId) {
        if (!gameLobbyStatus.equals(GameLobbyStatus.STARTED)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }
        if (playerIdPair.getFirst().equals(winnerId)) {
            this.gameLobbyResult = GameLobbyResult.PLAYER_1_WON;
        } else if (playerIdPair.getSecond().equals(winnerId)) {
            this.gameLobbyResult = GameLobbyResult.PLAYER_2_WON;
        } else {
            throw new IllegalArgumentException("Invalid player id provided");
        }
        this.endTime = LocalDateTime.now();
    }

    public void endGameWithDraw() {
        if (!gameLobbyStatus.equals(GameLobbyStatus.STARTED)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }
        this.gameLobbyResult = GameLobbyResult.DRAW;
        this.endTime = LocalDateTime.now();
    }

    public boolean isAgainstAi() {
        return playerIdPair.getSecond().isAI();
    }

    public GameLobbyId getGameLobbyId() {
        return gameLobbyId;
    }

    public GameReference getGameId() {
        return gameReference;
    }

    public Pair<PlayerId, PlayerId> getPlayerIdPair() {
        return playerIdPair;
    }

    public Boolean getPlayer1Accepted() {
        return player1Accepted;
    }

    public Boolean getPlayer2Accepted() {
        return player2Accepted;
    }

    public GameLobbyStatus getGameLobbyStatus() {
        return gameLobbyStatus;
    }

    public GameLobbyResult getGameLobbyResult() {
        return gameLobbyResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}

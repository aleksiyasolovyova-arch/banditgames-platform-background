package team11.platform_backend.gamesession.domain;

import java.time.LocalDate;

//Aggregate
public class GameResult {
    private final GameResultId gameResultId;
    private final GameId gameId;
    // When one of the players is null, it is an AI
    private final PlayerId player1Id;
    private final PlayerId player2Id;
    private GameSessionStatus gameSessionState;
    private final LocalDate date;
    private final int durationSeconds;

    // for getting (get methods)
    public GameResult(GameResultId gameResultId,
                      GameId gameId,
                      PlayerId player1Id,
                      PlayerId player2Id,
                      GameSessionStatus gameSessionState,
                      LocalDate date,
                      int durationSeconds) {
        this.gameResultId = gameResultId;
        this.gameId = gameId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameSessionState = gameSessionState;
        this.date = date;
        this.durationSeconds = durationSeconds;
    }

    // for creating (post methods)
    public GameResult(GameId gameId,
                      PlayerId player1Id,
                      PlayerId player2Id,
                      GameSessionStatus gameSessionState,
                      int durationSeconds) {
        this.gameResultId = GameResultId.createGameSessionId();
        this.gameId = gameId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameSessionState = gameSessionState;
        this.date=LocalDate.now();
        this.durationSeconds = durationSeconds;
    }

    public GameResultId getGameSessionId() {
        return gameResultId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public PlayerId getPlayer1Id() {
        return player1Id;
    }

    public PlayerId getPlayer2Id() {
        return player2Id;
    }

    public GameSessionStatus getGameSessionState() {
        return gameSessionState;
    }

    public LocalDate getDate() {return date;}

    public int getDurationSeconds() {return durationSeconds;}
}

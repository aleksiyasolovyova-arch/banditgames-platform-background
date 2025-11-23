package team11.platform_backend.player.domain.gamesession;

import team11.platform_backend.player.domain.player.PlayerId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Aggregate
public class GameSession {
    private final GameSessionId gameSessionId;
    private final GameId gameId;
    // When one of the players is null, it is an AI
    private final PlayerId player1Id;
    private final PlayerId player2Id;
    private final List<LocalDateTime> gameSessionTimes = new ArrayList<>();
    private final LocalDateTime startTime;

    private GameSessionStatus gameSessionState;
    private final List<GameState> gameStates = new ArrayList<>();

    // for getting (get methods)
    public GameSession(GameSessionId gameSessionId, GameId gameId, PlayerId player1Id, PlayerId player2Id, List<LocalDateTime> gameSessionTimes, LocalDateTime startTime, GameSessionStatus gameSessionState, List<GameState> gameStates) {
        this.gameSessionId = gameSessionId;
        this.gameId = gameId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameSessionTimes.addAll(gameSessionTimes);
        this.startTime = startTime;
        this.gameSessionState = gameSessionState;
        this.gameStates.addAll(gameStates);
    }

    // for creating (post methods)
    public GameSession( GameId gameId, PlayerId player1Id, PlayerId player2Id, List<LocalDateTime> gameSessionTimes, GameState gameState){
        this.gameSessionId = GameSessionId.createGameSessionId();
        this.gameId = gameId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameSessionTimes.addAll(gameSessionTimes);
        this.startTime = LocalDateTime.now();
        this.gameSessionState = GameSessionStatus.IN_PROGRESS;
        this.gameStates.add(gameState); // Add an initial empty board ( only sent if the ai player starts first ) 
    }

    //how to determine who won the game and is it a draw
    public void endGameSession() {

    }

    public void addGameState(GameState gameState) {
        this.gameStates.add(gameState);
    }


    public GameSessionId getGameSessionId() {
        return gameSessionId;
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

    public List<LocalDateTime> getGameSessionTimes() {
        return gameSessionTimes;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public GameSessionStatus getGameSessionState() {
        return gameSessionState;
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }
}

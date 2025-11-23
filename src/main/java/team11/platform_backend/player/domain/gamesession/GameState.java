package team11.platform_backend.player.domain.gamesession;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.time.LocalDateTime;

public class GameState {
    private final GameStateId gameStateId;
    private final LocalDateTime time;
    private final JSONObject board;
    private final PlayerTurn playerTurn;
    // remaining time

    // for posting (post methods)
    public GameState( JSONObject board, PlayerTurn playerTurn) {
        this.gameStateId = GameStateId.createGameStateId();
        this.time = LocalDateTime.now();
        this.board = board;
        this.playerTurn = playerTurn;
    }

    // for getting (get methods)
    public GameState(GameStateId gameStateId, LocalDateTime time, JSONObject board, PlayerTurn playerTurn) {
        this.gameStateId = gameStateId;
        this.time = time;
        this.board = board;
        this.playerTurn = playerTurn;
    }

    public GameStateId getGameStateId() {
        return gameStateId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public JSONObject getBoard() {
        return board;
    }

}

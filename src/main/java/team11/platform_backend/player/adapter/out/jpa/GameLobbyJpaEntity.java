package team11.platform_backend.player.adapter.out.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import team11.platform_backend.player.domain.gamelobby.GameResult;

@Entity
@Table(name = "game_lobbies", schema = "player_schema")
public class GameLobbyJpaEntity {

    @Id
    @Column(name = "game_lobby_id", updatable = false, nullable = false)
    private UUID gameLobbyId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "player1_id", nullable = false)
    private UUID playerId1;

    @Column(name = "player2_id", nullable = false)
    private UUID playerId2;

    @Column(name = "player1_accepted", nullable = false)
    private boolean player1Accepted;

    @Column(name = "player2_accepted", nullable = false)
    private boolean player2Accepted;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_result", nullable = false)
    private GameResult gameResult;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public GameLobbyJpaEntity() {
    }

    public UUID getGameLobbyId() {
        return gameLobbyId;
    }

    public void setGameLobbyId(UUID gameLobbyId) {
        this.gameLobbyId = gameLobbyId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId1() {
        return playerId1;
    }

    public void setPlayerId1(UUID playerId1) {
        this.playerId1 = playerId1;
    }

    public UUID getPlayerId2() {
        return playerId2;
    }

    public void setPlayerId2(UUID playerId2) {
        this.playerId2 = playerId2;
    }

    public boolean isPlayer1Accepted() {
        return player1Accepted;
    }

    public void setPlayer1Accepted(boolean player1Accepted) {
        this.player1Accepted = player1Accepted;
    }

    public boolean isPlayer2Accepted() {
        return player2Accepted;
    }

    public void setPlayer2Accepted(boolean player2Accepted) {
        this.player2Accepted = player2Accepted;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}


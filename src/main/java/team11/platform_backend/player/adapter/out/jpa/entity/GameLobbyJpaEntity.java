package team11.platform_backend.player.adapter.out.jpa.entity;

import jakarta.persistence.*;
import team11.platform_backend.player.domain.gamelobby.GameLobbyResult;
import team11.platform_backend.player.domain.gamelobby.GameLobbyStatus;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private Boolean player1Accepted;

    @Column(name = "player2_accepted", nullable = false)
    private Boolean player2Accepted;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_lobby_status", nullable = false)
    private GameLobbyStatus gameLobbyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_lobby_result", nullable = false)
    private GameLobbyResult gameLobbyResult;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public GameLobbyJpaEntity() {}

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

    public Boolean getPlayer1Accepted() {
        return player1Accepted;
    }

    public void setPlayer1Accepted(Boolean player1Accepted) {
        this.player1Accepted = player1Accepted;
    }

    public Boolean getPlayer2Accepted() {
        return player2Accepted;
    }

    public void setPlayer2Accepted(Boolean player2Accepted) {
        this.player2Accepted = player2Accepted;
    }

    public GameLobbyStatus getGameLobbyStatus() {
        return gameLobbyStatus;
    }

    public void setGameLobbyStatus(GameLobbyStatus gameLobbyStatus) {
        this.gameLobbyStatus = gameLobbyStatus;
    }

    public GameLobbyResult getGameLobbyResult() {
        return gameLobbyResult;
    }

    public void setGameLobbyResult(GameLobbyResult gameLobbyResult) {
        this.gameLobbyResult = gameLobbyResult;
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
package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema="read_model_schema", name="lobby")
public class LobbyModel {
    @Id
    @Column(name = "lobby_id")
    private UUID lobbyId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @Column(name = "player1_id", nullable = false)
    private UUID player1Id;

    @Column(name = "player2_id", nullable = false)
    private UUID player2Id;

    @Column(name = "player1_username")
    private String player1Username;

    @Column(name = "player2_username")
    private String player2Username;

    @Column(name = "player1_picture_url")
    private String player1PictureUrl;

    @Column(name = "player2_picture_url")
    private String player2PictureUrl;

    // Participation status
    @Column(name = "player1_status")
    private String player1Status;

    @Column(name = "player2_status")
    private String player2Status;

    // Lobby type
    @Column(name = "lobby_type")
    private String lobbyType;

    // Result
    @Column(name = "result")
    private String result;

    @Column(name = "winner_id")
    private UUID winnerId;

    // Timestamps
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    public LobbyModel() {}

    public UUID getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(UUID lobbyId) {
        this.lobbyId = lobbyId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(UUID player1Id) {
        this.player1Id = player1Id;
    }

    public UUID getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(UUID player2Id) {
        this.player2Id = player2Id;
    }


    public String getPlayer1Status() {
        return player1Status;
    }

    public void setPlayer1Status(String player1Status) {
        this.player1Status = player1Status;
    }

    public String getPlayer2Status() {
        return player2Status;
    }

    public void setPlayer2Status(String player2Status) {
        this.player2Status = player2Status;
    }

    public String getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(String lobbyType) {
        this.lobbyType = lobbyType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(UUID winnerId) {
        this.winnerId = winnerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public String getPlayer1PictureUrl() {
        return player1PictureUrl;
    }

    public void setPlayer1PictureUrl(String player1PictureUrl) {
        this.player1PictureUrl = player1PictureUrl;
    }

    public String getPlayer2PictureUrl() {
        return player2PictureUrl;
    }

    public void setPlayer2PictureUrl(String player2PictureUrl) {
        this.player2PictureUrl = player2PictureUrl;
    }
}

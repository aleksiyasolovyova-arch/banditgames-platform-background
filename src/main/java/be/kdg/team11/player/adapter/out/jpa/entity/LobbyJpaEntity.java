package be.kdg.team11.player.adapter.out.jpa.entity;

import be.kdg.team11.player.domain.lobby.LobbyResult;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lobby", schema = "player_schema")
public class LobbyJpaEntity {
    @Id
    private UUID lobbyId;

    @Column(nullable = false)
    private UUID gameId;

    @Column(nullable = false)
    private String gameUrl;

    @Column(nullable = false)
    private UUID player1Id;

    @Column(nullable = false)
    private UUID player2Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LobbyResult lobbyResult;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime endTime;

    public LobbyJpaEntity() {
    }

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

    public LobbyResult getLobbyResult() {
        return lobbyResult;
    }

    public void setLobbyResult(LobbyResult lobbyResult) {
        this.lobbyResult = lobbyResult;
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

    public String getGameUrl() {
        return gameUrl;
    }
    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
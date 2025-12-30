package be.kdg.team11.player.adapter.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "game_reference", schema = "player_schema")
public class GameReferenceJpaEntity {
    @Id
    private UUID gameId;

    @Column(nullable = false)
    private String gameUrl;

    public GameReferenceJpaEntity() {
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
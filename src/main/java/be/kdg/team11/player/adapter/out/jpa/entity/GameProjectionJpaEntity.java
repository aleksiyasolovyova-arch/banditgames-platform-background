package be.kdg.team11.player.adapter.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "game_projections")
public class GameProjectionJpaEntity {
    @Id
    @Column(name = "game_id", columnDefinition = "UUID")
    private UUID gameId;

    public GameProjectionJpaEntity() {
    }

    public GameProjectionJpaEntity(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}

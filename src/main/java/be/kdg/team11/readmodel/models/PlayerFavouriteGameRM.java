package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema="read_model_schema", name="player_favourite_game")
public class PlayerFavouriteGameRM {
    @Id
    private UUID playerId;
    @Column(nullable = false)
    private UUID gameId;

    public PlayerFavouriteGameRM() {}

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}

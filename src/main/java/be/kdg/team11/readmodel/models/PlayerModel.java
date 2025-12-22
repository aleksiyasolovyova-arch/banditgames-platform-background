package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema="read_model_schema", name="player_favourite_game")
public class PlayerModel {
    @Id
    private UUID playerId;

    @Column
    private UUID favouriteGameId;

    public PlayerModel() {}

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getFavouriteGameId() {
        return favouriteGameId;
    }

    public void setFavouriteGameId(UUID favouriteGameId) {
        this.favouriteGameId = favouriteGameId;
    }
}

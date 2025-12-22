package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema="read_model_schema", name="player")
public class PlayerModel {
    @Id
    private UUID playerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column
    private UUID favouriteGameId;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

package team11.platform_backend.player.adapter.out.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.UUID;

@Embeddable
public class OwnedGameEmbeddable {

    @Column(nullable = false)
    private UUID gameId;

    @Column(nullable = false)
    private boolean favourite;

    @Column(nullable = false)
    private LocalDate dateBought;

    public OwnedGameEmbeddable() {}

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public LocalDate getDateBought() {
        return dateBought;
    }

    public void setDateBought(LocalDate dateBought) {
        this.dateBought = dateBought;
    }
}

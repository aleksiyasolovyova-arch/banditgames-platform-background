package be.kdg.team11.player.adapter.out.jpa.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.UUID;

@Embeddable
public class OwnedGameEmbeddable {

    @Column(nullable = false)
    private UUID gameReference;

    @Column(nullable = false)
    private boolean favorite;

    @Column(nullable = false)
    private LocalDate dateBought;

    public OwnedGameEmbeddable() {
    }

    public UUID getGameReference() {
        return gameReference;
    }

    public void setGameReference(UUID gameReference) {
        this.gameReference = gameReference;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public LocalDate getDateBought() {
        return dateBought;
    }

    public void setDateBought(LocalDate dateBought) {
        this.dateBought = dateBought;
    }
}

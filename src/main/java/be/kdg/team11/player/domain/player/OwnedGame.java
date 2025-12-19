package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDate;

/**
 * Value Object representing a game owned by a player.
 */
public class OwnedGame {
    private final GameReference game;
    private final LocalDate dateBought;
    private boolean favorite;

    /**
     * Private constructor - use factory method instead.
     */
    private OwnedGame(GameReference game, LocalDate dateBought) {
        this.game = game;
        this.dateBought = dateBought;
        this.favorite = false;
    }

    /**
     * Factory method for creating a newly bought game.
     * Initial state: not favorite.
     */
    public static OwnedGame bought(GameReference game, LocalDate dateBought) {
        return new OwnedGame(game, dateBought);
    }

    public void favorite() {
        this.favorite = true;
    }

    public void unfavorite() {
        this.favorite = false;
    }

    public GameReference getGame() {
        return game;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public LocalDate getDateBought() {
        return dateBought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnedGame other)) return false;
        return game.equals(other.game);
    }

    @Override
    public int hashCode() {
        return game.hashCode();
    }
}


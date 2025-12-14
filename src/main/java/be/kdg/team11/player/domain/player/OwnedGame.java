package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDateTime;

public class OwnedGame {
    private final GameReference game;
    private final LocalDateTime dateBought;
    private boolean favorite;

    private OwnedGame(GameReference game, LocalDateTime dateBought) {
        this.game = game;
        this.dateBought = dateBought;
        this.favorite = false;
    }

    public static OwnedGame bought(GameReference game, LocalDateTime dateBought) {
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

    public LocalDateTime getDateBought() {
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


package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.game.Game;

public interface DeleteGamePort {
    void delete(Game game);
}

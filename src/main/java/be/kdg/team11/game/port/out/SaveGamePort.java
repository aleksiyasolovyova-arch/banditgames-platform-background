package be.kdg.team11.game.port.out;

import be.kdg.team11.game.domain.game.Game;

public interface SaveGamePort {
    Game save(Game game);
}

package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.game.Game;

public interface SaveGamePort {
    Game save(Game game);
}

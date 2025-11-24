package team11.platform_backend.game.port.in;

import team11.platform_backend.game.domain.game.Game;

public interface AcceptGamePort {
    Game acceptGame(AcceptGameCommand command);
}

package be.kdg.team11.game.port.in;

import be.kdg.team11.game.domain.game.Game;

public interface AcceptGamePort {
    Game acceptGame(AcceptGameCommand command);
}

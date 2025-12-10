package be.kdg.team11.game.port.in;

import be.kdg.team11.game.domain.game.Game;

public interface RejectGamePort {
    Game rejectGame(RejectGameCommand command);
}

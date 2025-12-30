package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.Game;

public interface ModifyGameUrlsPort {
    Game modify(ModifyGameUrlsCommand command);
}

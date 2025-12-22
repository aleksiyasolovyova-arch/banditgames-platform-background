package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.Game;

import java.util.List;

public interface FindAllGamesQueryPort {
    List<Game> showAll();
}

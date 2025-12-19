package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.game.Game;

import java.util.List;

public interface LoadGamesPort {
    List<Game> loadAll();
}

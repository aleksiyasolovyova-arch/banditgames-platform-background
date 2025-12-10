package be.kdg.team11.game.port.out;

import be.kdg.team11.game.domain.game.Game;
import be.kdg.team11.game.domain.game.GameId;

import java.util.Optional;

public interface LoadGamePort {
    Optional<Game> loadBy(GameId gameId);
}

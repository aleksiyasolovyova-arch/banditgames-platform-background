package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;

import java.util.Optional;

public interface LoadGamePort {
    Optional<Game> loadBy(GameId gameId);
}

package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;

import java.util.Optional;

public interface LoadGamePort {
    Optional<Game> findById(GameId gameId);
}

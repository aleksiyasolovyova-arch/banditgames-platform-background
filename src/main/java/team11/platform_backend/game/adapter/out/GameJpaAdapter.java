package team11.platform_backend.game.adapter.out;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.GameJpaEntity;
import team11.platform_backend.game.adapter.out.jpa.GameJpaRepository;
import team11.platform_backend.game.adapter.out.mapper.GameJpaMapper;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.port.out.LoadGamePort;
import team11.platform_backend.game.port.out.SaveGamePort;

import java.util.Optional;

@Component
public class GameJpaAdapter implements SaveGamePort, LoadGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final GameJpaMapper gameJpaMapper;

    public GameJpaAdapter(GameJpaRepository gameJpaRepository, GameJpaMapper gameJpaMapper) {
        this.gameJpaRepository = gameJpaRepository;
        this.gameJpaMapper = gameJpaMapper;
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity entity = gameJpaMapper.toJpaEntity(game);
        GameJpaEntity saved = gameJpaRepository.save(entity);
        return gameJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Game> loadBy(GameId gameId) {
        return gameJpaRepository.findById(gameId.gameId())
                .map(gameJpaMapper::toDomain);
    }
}

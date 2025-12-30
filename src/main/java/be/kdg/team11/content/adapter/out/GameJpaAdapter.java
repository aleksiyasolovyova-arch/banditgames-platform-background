package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.adapter.out.jpa.GameJpaEntity;
import be.kdg.team11.content.adapter.out.jpa.GameJpaRepository;
import be.kdg.team11.content.adapter.out.mapper.GameJpaMapper;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import org.springframework.stereotype.Component;

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

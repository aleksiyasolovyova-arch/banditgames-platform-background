package be.kdg.team11.game.adapter.out;

import be.kdg.team11.game.port.out.DeleteGamePort;
import org.springframework.stereotype.Component;
import be.kdg.team11.game.adapter.out.jpa.GameJpaEntity;
import be.kdg.team11.game.adapter.out.jpa.GameJpaRepository;
import be.kdg.team11.game.adapter.out.mapper.GameJpaMapper;
import be.kdg.team11.game.domain.game.Game;
import be.kdg.team11.game.domain.game.GameId;
import be.kdg.team11.game.port.out.LoadGamePort;
import be.kdg.team11.game.port.out.SaveGamePort;

import java.util.Optional;

@Component
public class GameJpaAdapter implements SaveGamePort, LoadGamePort, DeleteGamePort {
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

    @Override
    public void delete(Game game) {
        gameJpaRepository.delete(gameJpaMapper.toJpaEntity(game));
    }
}

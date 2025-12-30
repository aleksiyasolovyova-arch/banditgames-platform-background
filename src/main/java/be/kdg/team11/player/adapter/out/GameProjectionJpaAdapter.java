package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.GameReferenceJpaRepository;
import be.kdg.team11.player.adapter.out.jpa.entity.GameReferenceJpaEntity;
import be.kdg.team11.player.adapter.out.mapper.GameReferenceJpaMapper;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SaveGameReferencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameProjectionJpaAdapter implements GameReferenceExistsPort, SaveGameReferencePort, LoadGameReferencePort {
    private final GameReferenceJpaRepository gameReferenceJpaRepository;
    private final GameReferenceJpaMapper gameReferenceJpaMapper;

    public GameProjectionJpaAdapter(GameReferenceJpaRepository gameReferenceJpaRepository,
                                    GameReferenceJpaMapper gameReferenceJpaMapper) {
        this.gameReferenceJpaRepository = gameReferenceJpaRepository;
        this.gameReferenceJpaMapper = gameReferenceJpaMapper;
    }

    @Override
    public boolean exists(GameReference gameReference) {
        return gameReferenceJpaRepository.existsById(gameReferenceJpaMapper.toJpaEntity(gameReference).getGameId());
    }

    @Override
    public GameReference save(GameReference gameReference) {
        return gameReferenceJpaMapper.toDomain(gameReferenceJpaRepository.save(gameReferenceJpaMapper.toJpaEntity(gameReference)));
    }

    @Override
    public List<GameReference> loadAll() {
        return gameReferenceJpaRepository.findAll()
                .stream()
                .map(gameReferenceJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameReference> loadBy(UUID gameId) {
        return gameReferenceJpaRepository.findById(gameId)
                .map(gameReferenceJpaMapper::toDomain);
    }
}

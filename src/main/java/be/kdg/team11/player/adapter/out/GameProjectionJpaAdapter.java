package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.GameReferenceJpaRepository;
import be.kdg.team11.player.adapter.out.mapper.GameReferenceJpaMapper;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.SaveGameReferencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameProjectionJpaAdapter implements GameReferenceExistsPort, SaveGameReferencePort {
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
}

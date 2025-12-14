package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.GameReferenceJpaRepository;
import be.kdg.team11.player.adapter.out.mapper.GameReferenceJpaMapper;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameProjectionJpaAdapter implements GameReferenceExistsPort {
    private final GameReferenceJpaRepository gameReferenceJpaRepository;
    private final GameReferenceJpaMapper gameReferenceJpaMapper;

    public GameProjectionJpaAdapter(GameReferenceJpaRepository gameReferenceJpaRepository,
                                    GameReferenceJpaMapper gameReferenceJpaMapper) {
        this.gameReferenceJpaRepository = gameReferenceJpaRepository;
        this.gameReferenceJpaMapper = gameReferenceJpaMapper;
    }

    @Override
    public List<GameProjection> loadAll() {
        return gameReferenceJpaRepository.findAll()
                .stream()
                .map(gameReferenceJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}

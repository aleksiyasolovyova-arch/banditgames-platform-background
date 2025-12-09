package team11.platform_backend.player.adapter.out;

import org.springframework.stereotype.Service;
import team11.platform_backend.player.adapter.out.mapper.GameProjectionJpaMapper;
import team11.platform_backend.player.adapter.out.jpa.GameProjectionJpaRepository;
import team11.platform_backend.player.domain.projections.GameProjection;
import team11.platform_backend.player.port.out.LoadGameProjectionsPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameProjectionJpaAdapter implements LoadGameProjectionsPort {
    private final GameProjectionJpaRepository gameProjectionJpaRepository;
    private final GameProjectionJpaMapper gameProjectionJpaMapper;

    public GameProjectionJpaAdapter(GameProjectionJpaRepository gameProjectionJpaRepository,
                                    GameProjectionJpaMapper gameProjectionJpaMapper) {
        this.gameProjectionJpaRepository = gameProjectionJpaRepository;
        this.gameProjectionJpaMapper = gameProjectionJpaMapper;
    }

    @Override
    public List<GameProjection> loadAllGameProjections() {
        return gameProjectionJpaRepository.findAll()
                .stream()
                .map(gameProjectionJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}

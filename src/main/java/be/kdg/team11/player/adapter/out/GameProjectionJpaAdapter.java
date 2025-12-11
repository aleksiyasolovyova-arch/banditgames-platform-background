package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.GameProjectionJpaRepository;
import be.kdg.team11.player.adapter.out.mapper.GameProjectionJpaMapper;
import be.kdg.team11.player.port.out.LoadGameProjectionsPort;
import org.springframework.stereotype.Service;

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
    public List<GameProjection> loadAll() {
        return gameProjectionJpaRepository.findAll()
                .stream()
                .map(gameProjectionJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}

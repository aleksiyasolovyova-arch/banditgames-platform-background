package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.adapter.out.jpa.PlayerStatisticsJpaEntity;
import be.kdg.team11.content.adapter.out.jpa.PlayerStatisticsJpaRepository;
import be.kdg.team11.content.adapter.out.mapper.PlayerStatisticsJpaMapper;
import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.content.port.out.LoadPlayerStatisticsPort;
import be.kdg.team11.content.port.out.SavePlayerStatisticsPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PlayerStatisticsJpaAdapter implements SavePlayerStatisticsPort, LoadPlayerStatisticsPort {
    private final PlayerStatisticsJpaRepository playerStatisticsJpaRepository;
    private final PlayerStatisticsJpaMapper playerStatisticsJpaMapper;

    public PlayerStatisticsJpaAdapter(PlayerStatisticsJpaRepository playerStatisticsJpaRepository, PlayerStatisticsJpaMapper playerStatisticsJpaMapper) {
        this.playerStatisticsJpaRepository = playerStatisticsJpaRepository;
        this.playerStatisticsJpaMapper = playerStatisticsJpaMapper;
    }

    @Override
    public PlayerStatistics save(PlayerStatistics playerStatistics) {
        PlayerStatisticsJpaEntity entity = playerStatisticsJpaRepository.save(playerStatisticsJpaMapper.toJpaEntity(playerStatistics));
        return playerStatisticsJpaMapper.toDomain(entity);
    }

    @Override
    public Optional<PlayerStatistics> loadBy(UUID playerId) {
        return playerStatisticsJpaRepository.findById(playerId).map(playerStatisticsJpaMapper::toDomain);
    }
}

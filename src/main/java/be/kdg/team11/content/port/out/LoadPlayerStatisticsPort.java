package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.projections.PlayerStatistics;

import java.util.Optional;
import java.util.UUID;

public interface LoadPlayerStatisticsPort {
    Optional<PlayerStatistics> loadBy(UUID playerId);
}

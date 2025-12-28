package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.projections.PlayerStatistics;

public interface SavePlayerStatisticsPort {
    PlayerStatistics save(PlayerStatistics playerStatistics);
}

package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.content.port.in.PlayerCreatedCommand;
import be.kdg.team11.content.port.in.PlayerCreatedProjector;
import be.kdg.team11.content.port.out.SavePlayerStatisticsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerCreatedProjectorImpl implements PlayerCreatedProjector {
    private final List<SavePlayerStatisticsPort> savePlayerStatisticsPorts;

    public PlayerCreatedProjectorImpl(List<SavePlayerStatisticsPort> savePlayerStatisticsPorts) {
        this.savePlayerStatisticsPorts = savePlayerStatisticsPorts;
    }

    @Override
    public void project(PlayerCreatedCommand command) {
        PlayerStatistics statistics = PlayerStatistics.create(command.playerId());
        savePlayerStatisticsPorts.forEach(savePlayerStatisticsPort -> savePlayerStatisticsPort.save(statistics));
    }
}

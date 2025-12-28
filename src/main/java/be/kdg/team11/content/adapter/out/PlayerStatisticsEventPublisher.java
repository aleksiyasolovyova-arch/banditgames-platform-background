package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.content.port.out.SavePlayerStatisticsPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatisticsEventPublisher implements SavePlayerStatisticsPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PlayerStatisticsEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public PlayerStatistics save(PlayerStatistics playerStatistics) {
        playerStatistics.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return playerStatistics;
    }
}

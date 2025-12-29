package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.port.out.SavePlatformAchievementPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PlatformAchievementEventPublisher implements SavePlatformAchievementPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PlatformAchievementEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public PlatformAchievement save(PlatformAchievement platformAchievement) {
        platformAchievement.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return platformAchievement;
    }


}

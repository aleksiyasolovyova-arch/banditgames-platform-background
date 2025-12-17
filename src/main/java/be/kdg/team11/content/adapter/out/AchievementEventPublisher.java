package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.out.SaveAchievementPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventPublisher implements SaveAchievementPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public AchievementEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Achievement save(Achievement achievement) {
        achievement.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return achievement;
    }


}

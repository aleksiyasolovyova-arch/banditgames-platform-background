package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.achievement.AchievementModelService;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventListener {
    private final AchievementModelService achievementModelService;

    public AchievementEventListener(AchievementModelService achievementModelService) {
        this.achievementModelService = achievementModelService;
    }

    @EventListener(AchievementCreatedEvent.class)
    public void achievementCreated(AchievementCreatedEvent event) {
        achievementModelService.project(event);
    }

}

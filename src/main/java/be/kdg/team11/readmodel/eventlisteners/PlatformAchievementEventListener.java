package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.platformachievement.PlatformAchievementModelService;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PlatformAchievementEventListener {
    private final PlatformAchievementModelService platformAchievementModelService;

    public PlatformAchievementEventListener(PlatformAchievementModelService platformAchievementModelService) {
        this.platformAchievementModelService = platformAchievementModelService;
    }

    @EventListener(PlatformAchievementCreatedEvent.class)
    public void platformAchievementCreated(PlatformAchievementCreatedEvent event) {
        platformAchievementModelService.project(event);
    }

}

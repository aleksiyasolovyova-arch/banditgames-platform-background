package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.port.in.AchievementUnlockedCommand;
import be.kdg.team11.player.port.in.AchievementUnlockedProjector;
import be.kdg.team11.sharedkernel.events.achievement.AchievementUnlockedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AchievementUnlockedEventListener {
    private final AchievementUnlockedProjector achievementUnlockedProjector;

    public AchievementUnlockedEventListener(AchievementUnlockedProjector achievementUnlockedProjector) {
        this.achievementUnlockedProjector = achievementUnlockedProjector;
    }

    @EventListener(AchievementUnlockedEvent.class)
    public void achievementUnlocked(AchievementUnlockedEvent event){
        achievementUnlockedProjector.project(new AchievementUnlockedCommand(event.playerId(),event.achievementId(),event.eventPit()));
    }
}

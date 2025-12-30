package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.port.in.PlatformAchievementUnlockedCommand;
import be.kdg.team11.player.port.in.PlatformAchievementUnlockedProjector;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementUnlockedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AchievementUnlockedListener {
    private final PlatformAchievementUnlockedProjector platformAchievementUnlockedProjector;

    public AchievementUnlockedListener(PlatformAchievementUnlockedProjector platformAchievementUnlockedProjector) {
        this.platformAchievementUnlockedProjector = platformAchievementUnlockedProjector;
    }

    @EventListener(PlatformAchievementUnlockedEvent.class)
    public void platformAchievementUnlocked(PlatformAchievementUnlockedEvent event) {
        platformAchievementUnlockedProjector.project(new PlatformAchievementUnlockedCommand(event.playerId(), event.achievementId(), event.eventPit()));
    }
}

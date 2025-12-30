package be.kdg.team11.player.port.in;

import be.kdg.team11.sharedkernel.events.rabbitmq.AchievementUnlockedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameCreatedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameFinishedEvent;

public interface GameEventPort {
    void onAchievementUnlocked(AchievementUnlockedEvent event);
    void onGameCreatedEvent(GameCreatedEvent event);
    void onGameFinishedEvent(GameFinishedEvent event);
}

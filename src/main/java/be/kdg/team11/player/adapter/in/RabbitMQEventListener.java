package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.port.in.GameEventPort;
import be.kdg.team11.sharedkernel.config.rabbitmq.RabbitMQTopology;
import be.kdg.team11.sharedkernel.events.rabbitmq.AchievementUnlockedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameCreatedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameFinishedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventListener {
    private final GameEventPort gameEventPort;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQEventListener.class);

    public RabbitMQEventListener(GameEventPort gameEventPort) {
        this.gameEventPort = gameEventPort;
    }

    @RabbitListener(queues = RabbitMQTopology.GAME_CREATED_QUEUE)
    public void handleGameCreated(GameCreatedEvent event) {
        logger.info("Received game.created event for game: {}", event.gameId());
        gameEventPort.onGameCreatedEvent(event);
    }

    @RabbitListener(queues = RabbitMQTopology.GAME_FINISHED_QUEUE)
    public void handleGameFinished(GameFinishedEvent event) {
        logger.info("Received game.finished event for game: {}", event.gameId());
        gameEventPort.onGameFinishedEvent(event);
    }

    @RabbitListener(queues = RabbitMQTopology.ACHIEVEMENT_UNLOCKED_QUEUE)
    public void handleAchievementUnlocked(AchievementUnlockedEvent event) {
        logger.info("Received achievement.unlocked event for player: {}", event.playerId());
        gameEventPort.onAchievementUnlocked(event);
    }
}

package be.kdg.team11.sharedkernel.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    // Platform-wide exchange (shared by all games)
    public static final String PLATFORM_EXCHANGE = "platform.events";

    // Queues for this service
    public static final String GAME_CREATED_QUEUE = "platform.game.created.queue";
    public static final String GAME_FINISHED_QUEUE = "platform.game.finished.queue";
    public static final String ACHIEVEMENT_UNLOCKED_QUEUE = "platform.achievement.unlocked.queue";

    @Bean
    public TopicExchange platformExchange() {
        return ExchangeBuilder
                .topicExchange(PLATFORM_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue gameCreatedQueue() {
        return QueueBuilder
                .durable(GAME_CREATED_QUEUE)
                .build();
    }

    @Bean
    public Queue gameFinishedQueue() {
        return QueueBuilder
                .durable(GAME_FINISHED_QUEUE)
                .build();
    }

    @Bean
    public Queue achievementUnlockedQueue() {
        return QueueBuilder
                .durable(ACHIEVEMENT_UNLOCKED_QUEUE)
                .build();
    }

    @Bean
    public Binding gameCreatedBinding(Queue gameCreatedQueue, TopicExchange platformExchange) {
        return BindingBuilder
                .bind(gameCreatedQueue)
                .to(platformExchange)
                .with("*.game.created");  // Matches connect4.game.created, chess.game.created, etc.
    }

    @Bean
    public Binding gameFinishedBinding(Queue gameFinishedQueue, TopicExchange platformExchange) {
        return BindingBuilder
                .bind(gameFinishedQueue)
                .to(platformExchange)
                .with("*.game.finished");
    }

    @Bean
    public Binding achievementUnlockedBinding(Queue achievementUnlockedQueue, TopicExchange platformExchange) {
        return BindingBuilder
                .bind(achievementUnlockedQueue)
                .to(platformExchange)
                .with("*.achievement.unlocked");
    }
}
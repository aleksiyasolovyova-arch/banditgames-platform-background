package be.kdg.team11.sharedkernel.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    public static final String EXCHANGE = "connect4.events";
    public static final String GAME_CREATED_QUEUE = "game.created.queue";
    public static final String GAME_FINISHED_QUEUE = "game.finished.queue";
    public static final String ACHIEVEMENT_UNLOCKED_QUEUE = "achievement.unlocked.queue";

    @Bean
    public TopicExchange connect4Exchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE)
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
    public Binding gameCreatedBinding(Queue gameCreatedQueue, TopicExchange connect4Exchange) {
        return BindingBuilder
                .bind(gameCreatedQueue)
                .to(connect4Exchange)
                .with("game.created");
    }

    @Bean
    public Binding gameFinishedBinding(Queue gameFinishedQueue, TopicExchange connect4Exchange) {
        return BindingBuilder
                .bind(gameFinishedQueue)
                .to(connect4Exchange)
                .with("game.finished");
    }

    @Bean
    public Binding achievementUnlockedBinding(Queue achievementUnlockedQueue, TopicExchange connect4Exchange) {
        return BindingBuilder
                .bind(achievementUnlockedQueue)
                .to(connect4Exchange)
                .with("achievement.unlocked");
    }
}
package be.kdg.team11.acl.chess.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChessRabbitMQTopology {

    // Chess game exchange (from the chess application README)
    public static final String CHESS_EXCHANGE = "gameExchange";

    // ACL queues - dedicated queues for translating chess events
    public static final String CHESS_GAME_CREATED_QUEUE = "acl.chess.game.created.queue";
    public static final String CHESS_GAME_ENDED_QUEUE = "acl.chess.game.ended.queue";
    public static final String CHESS_GAME_REGISTERED_QUEUE = "acl.chess.game.registered.queue";  // ✅ Added
    public static final String CHESS_ACHIEVEMENT_QUEUE = "acl.chess.achievement.acquired.queue";

    @Bean
    public TopicExchange chessGameExchange() {
        return ExchangeBuilder
                .topicExchange(CHESS_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue chessGameCreatedQueue() {
        return QueueBuilder
                .durable(CHESS_GAME_CREATED_QUEUE)
                .build();
    }

    @Bean
    public Queue chessGameEndedQueue() {
        return QueueBuilder
                .durable(CHESS_GAME_ENDED_QUEUE)
                .build();
    }

    @Bean
    public Queue chessGameRegisteredQueue() {  // ✅ Added
        return QueueBuilder
                .durable(CHESS_GAME_REGISTERED_QUEUE)
                .build();
    }

    @Bean
    public Queue chessAchievementQueue() {
        return QueueBuilder
                .durable(CHESS_ACHIEVEMENT_QUEUE)
                .build();
    }

    @Bean
    public Binding chessGameCreatedBinding(Queue chessGameCreatedQueue, TopicExchange chessGameExchange) {
        return BindingBuilder
                .bind(chessGameCreatedQueue)
                .to(chessGameExchange)
                .with("game.created");
    }

    @Bean
    public Binding chessGameEndedBinding(Queue chessGameEndedQueue, TopicExchange chessGameExchange) {
        return BindingBuilder
                .bind(chessGameEndedQueue)
                .to(chessGameExchange)
                .with("game.ended");
    }

    @Bean
    public Binding chessGameRegisteredBinding(Queue chessGameRegisteredQueue, TopicExchange chessGameExchange) {  // ✅ Added
        return BindingBuilder
                .bind(chessGameRegisteredQueue)
                .to(chessGameExchange)
                .with("game.registered");
    }

    @Bean
    public Binding chessAchievementBinding(Queue chessAchievementQueue, TopicExchange chessGameExchange) {
        return BindingBuilder
                .bind(chessAchievementQueue)
                .to(chessGameExchange)
                .with("achievement.acquired");
    }
}
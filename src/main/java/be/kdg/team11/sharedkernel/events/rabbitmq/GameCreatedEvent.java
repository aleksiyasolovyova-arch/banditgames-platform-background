package be.kdg.team11.sharedkernel.events.rabbitmq;

import be.kdg.team11.sharedkernel.events.RabbitMQEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record GameCreatedEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("eventType") String eventType,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("gameId") String gameId,
        @JsonProperty("board") Board board,
        @JsonProperty("playerOne") Player playerOne,
        @JsonProperty("playerTwo") Player playerTwo,
        @JsonProperty("phase") String phase
) implements RabbitMQEvent {
    public record Board(int rows, int cols) {}
    public record Player(String id, String name, String type) {}
}

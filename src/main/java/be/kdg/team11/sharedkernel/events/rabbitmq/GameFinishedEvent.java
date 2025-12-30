package be.kdg.team11.sharedkernel.events.rabbitmq;

import be.kdg.team11.sharedkernel.events.RabbitMQEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record GameFinishedEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("eventType") String eventType,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("gameId") String gameId,
        @JsonProperty("phase") String phase,
        @JsonProperty("winner") Player winner,
        @JsonProperty("totalMoves") int totalMoves,
        @JsonProperty("durationSeconds") double durationSeconds
) implements RabbitMQEvent {
    public record Player(String id, String name, String type) {}
}

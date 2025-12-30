package be.kdg.team11.sharedkernel.events.rabbitmq;

import be.kdg.team11.sharedkernel.events.RabbitMQEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record AchievementUnlockedEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("eventType") String eventType,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("playerId") String playerId,
        @JsonProperty("gameId") String gameId,
        @JsonProperty("achievementType") String achievementType,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description
) implements RabbitMQEvent {}
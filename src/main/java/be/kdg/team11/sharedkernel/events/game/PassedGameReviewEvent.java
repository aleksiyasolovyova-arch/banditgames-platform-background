package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PassedGameReviewEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId
) implements DomainEvent {
    public PassedGameReviewEvent(
            UUID gameId
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId
        );
    }
}

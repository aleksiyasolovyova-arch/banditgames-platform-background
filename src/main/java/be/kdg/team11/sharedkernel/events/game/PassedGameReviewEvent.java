package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PassedGameReviewEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String newReviewState
) implements DomainEvent {
    public PassedGameReviewEvent(
            UUID gameId, String newReviewState
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                newReviewState
        );
    }
}

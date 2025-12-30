package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PassedGameReviewEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String gameUrl,
        String newReviewState
) implements DomainEvent {
    public PassedGameReviewEvent(
            UUID gameId, String gameUrl, String newReviewState
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                gameUrl,
                newReviewState
        );
    }
}

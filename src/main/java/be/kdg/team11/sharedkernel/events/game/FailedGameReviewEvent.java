package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FailedGameReviewEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String newReviewStatus
) implements DomainEvent {
    public FailedGameReviewEvent(UUID gameId, String newReviewStatus) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                newReviewStatus
        );
    }
}

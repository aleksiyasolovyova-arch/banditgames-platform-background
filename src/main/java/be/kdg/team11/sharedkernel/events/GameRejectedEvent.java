package be.kdg.team11.sharedkernel.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameRejectedEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId
) implements DomainEvent {
    public GameRejectedEvent(UUID gameId) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId
        );
    }
}

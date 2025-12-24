package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record BefriendedPlayerEvent(
        UUID friendshipId,
        UUID acceptedBy,
        LocalDateTime eventPit,
        String newState
) implements DomainEvent {
    public BefriendedPlayerEvent(UUID friendshipId,
                                 UUID acceptedBy,
                                 String newState) {
        this(friendshipId,
                acceptedBy,
                LocalDateTime.now(),
                newState);
    }
}

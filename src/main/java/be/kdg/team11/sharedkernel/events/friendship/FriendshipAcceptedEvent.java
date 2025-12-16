package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipAcceptedEvent(
        UUID friendshipId,
        UUID acceptedBy,
        LocalDateTime eventPit
) implements DomainEvent {
    public FriendshipAcceptedEvent(UUID friendshipId,
                                   UUID acceptedBy) {
        this(friendshipId,
                acceptedBy,
                LocalDateTime.now());
    }
}

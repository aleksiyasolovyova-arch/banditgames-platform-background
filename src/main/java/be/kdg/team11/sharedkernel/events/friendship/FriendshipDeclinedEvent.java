package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipDeclinedEvent(
        UUID friendshipId,
        UUID rejectedBy,
        LocalDateTime eventPit,
        String newState
) implements DomainEvent {
    public FriendshipDeclinedEvent(UUID friendshipId,
                                   UUID rejectedBy,
                                   String newState) {
        this(friendshipId,
                rejectedBy,
                LocalDateTime.now(),
                newState);
    }
}

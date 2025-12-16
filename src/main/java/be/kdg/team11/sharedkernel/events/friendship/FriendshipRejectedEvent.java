package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipRejectedEvent(
        UUID friendshipId,
        UUID rejectedBy,
        LocalDateTime eventPit
) implements DomainEvent {
    public FriendshipRejectedEvent(UUID friendshipId,
                                   UUID rejectedBy) {
        this(friendshipId,
                rejectedBy,
                LocalDateTime.now());
    }
}

package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipEndEvent(
        UUID friendshipId,
        UUID initiatedBy,
        LocalDateTime eventPit
) implements DomainEvent {
    public FriendshipEndEvent(UUID friendshipId,
                              UUID initiatedBy) {
        this(friendshipId,
                initiatedBy,
                LocalDateTime.now());
    }
}

package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipEndEvent(
        UUID friendshipId,
        UUID initiatedBy,
        LocalDateTime eventPit,
        String newState
) implements DomainEvent {
    public FriendshipEndEvent(UUID friendshipId,
                              UUID initiatedBy,
                              String newState) {
        this(friendshipId,
                initiatedBy,
                LocalDateTime.now(),
                newState);
    }
}

package be.kdg.team11.sharedkernel.events.friendship;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipEndEvent(
        UUID friendshipId,
        UUID requesterId,
        UUID recipientId,
        LocalDateTime eventPit
) implements DomainEvent {
    public FriendshipEndEvent(UUID friendshipId,
                              UUID requesterId,
                              UUID recipientId) {
        this(friendshipId,
                requesterId,
                recipientId,
                LocalDateTime.now());
    }
}

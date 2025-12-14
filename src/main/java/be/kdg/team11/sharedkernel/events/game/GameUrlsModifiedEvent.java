package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameUrlsModifiedEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String newPictureUrl,
        String newGameUrl
)implements DomainEvent {
    public GameUrlsModifiedEvent(
            UUID gameId,
            String newPictureUrl,
            String newGameUrl
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                newPictureUrl,
                newGameUrl
        );
    }
}

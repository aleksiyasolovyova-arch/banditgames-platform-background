package be.kdg.team11.sharedkernel.events.player;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerChangedPictureUrlEvent(
        UUID playerId,
        String pictureUrl,
        LocalDate eventPit
) implements DomainEvent {
    public PlayerChangedPictureUrlEvent(
            UUID playerId,
            String pictureUrl
    ) {
        this(
                playerId,
                pictureUrl,
                LocalDate.now()
        );
    }
}

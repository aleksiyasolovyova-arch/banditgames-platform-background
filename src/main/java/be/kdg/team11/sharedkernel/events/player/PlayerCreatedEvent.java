package be.kdg.team11.sharedkernel.events.player;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerCreatedEvent(
        UUID playerId,
        LocalDate joinedDate,
        LocalDate eventPit
)implements DomainEvent {
    public PlayerCreatedEvent(UUID playerId, LocalDate joinedDate) {
        this(playerId,joinedDate, LocalDate.now());
    }

}

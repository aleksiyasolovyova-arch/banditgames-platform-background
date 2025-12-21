package be.kdg.team11.sharedkernel.events.player;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerRemovedFavoriteGameEvent(
        UUID playerId,
        LocalDate eventPit
)implements DomainEvent {
    public PlayerRemovedFavoriteGameEvent(UUID playerId) {
        this(playerId,
                LocalDate.now());
    }
}

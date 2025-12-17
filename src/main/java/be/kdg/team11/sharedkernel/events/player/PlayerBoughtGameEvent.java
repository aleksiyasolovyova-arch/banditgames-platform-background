package be.kdg.team11.sharedkernel.events.player;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerBoughtGameEvent(
        UUID playerId,
        UUID gameId,
        LocalDate purchaseDate,
        LocalDate eventPit
)implements DomainEvent {
    public PlayerBoughtGameEvent(UUID playerId,
                                 UUID gameId,
                                 LocalDate purchaseDate) {
        this(playerId,
                gameId,
                purchaseDate,
                LocalDate.now());
    }
}

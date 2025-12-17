package be.kdg.team11.sharedkernel.events.player;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerUnfavoritedGameEvent(
        UUID playerId,
        UUID gameId,
        LocalDate eventPit
)implements DomainEvent {
    public PlayerUnfavoritedGameEvent(UUID playerId,
                                      UUID gameId) {
        this(playerId,
                gameId,
                LocalDate.now());
    }
}

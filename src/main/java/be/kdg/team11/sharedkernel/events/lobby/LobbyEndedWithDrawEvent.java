package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyEndedWithDrawEvent(
        UUID lobbyId,
        String newStatus,
        LocalDateTime eventPit
)implements DomainEvent {
    public LobbyEndedWithDrawEvent(UUID lobbyId,
                                   String newStatus) {
        this(lobbyId,
                newStatus,
                LocalDateTime.now());
    }
}

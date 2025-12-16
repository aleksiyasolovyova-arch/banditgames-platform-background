package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyRejectedEvent(
        UUID lobbyId,
        UUID playerId,
        String newStatus,
        LocalDateTime eventPit
) implements DomainEvent {
    public LobbyRejectedEvent(UUID lobbyId,
                              UUID playerId,
                              String newStatus) {
        this(lobbyId,
                playerId,
                newStatus,
                LocalDateTime.now());
    }

}

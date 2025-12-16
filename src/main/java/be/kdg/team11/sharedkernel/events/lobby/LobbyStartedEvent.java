package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

//TODO figure out better thing for status
public record LobbyStartedEvent(
        UUID lobbyId,
        String previousStatus,
        String newStatus,
        LocalDateTime eventPit
)implements DomainEvent {
    public LobbyStartedEvent(UUID lobbyId,
                             String previousStatus,
                             String newStatus) {
        this(lobbyId,
                previousStatus,
                newStatus,
                LocalDateTime.now());
    }
}

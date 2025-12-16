package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyEndedWithWinnerEvent(
        UUID lobbyId,
        UUID winnerId,
        String newStatus,
        LocalDateTime eventPit
) implements DomainEvent {
    public LobbyEndedWithWinnerEvent(UUID lobbyId,
                                     UUID winnerId,
                                     String newStatus) {
        this(lobbyId,
                winnerId,
                newStatus,
                LocalDateTime.now());
    }
}

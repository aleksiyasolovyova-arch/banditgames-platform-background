package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyCreatedEvent(
        UUID lobbyId,
        UUID gameId,
        UUID player1Id,
        UUID player2Id,
        String player1Status,
        String player2Status,
        String lobbyType,
        String initialStatus,
        LocalDateTime eventPit
) implements DomainEvent {
    public LobbyCreatedEvent(UUID lobbyId,
                             UUID gameId,
                             UUID player1Id,
                             UUID player2Id,
                             String player1Status,
                             String player2Status,
                             String lobbyType,
                             String initialStatus) {
        this(lobbyId,
                gameId,
                player1Id,
                player2Id,
                player1Status,
                player2Status,
                lobbyType,
                initialStatus,
                LocalDateTime.now());
    }
}

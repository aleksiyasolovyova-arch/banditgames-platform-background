package be.kdg.team11.sharedkernel.events.lobby;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record LobbyEndedWithWinnerEvent(
        UUID lobbyId,
        UUID winnerId,
        UUID player1Id,
        UUID player2Id,
        long time,
        LocalDateTime eventPit
) implements DomainEvent {
    public LobbyEndedWithWinnerEvent(UUID lobbyId,
                                     UUID winnerId,
                                     UUID player1Id,
                                     UUID player2Id,
                                     long time) {
        this(lobbyId,
                winnerId,
                player1Id,
                player2Id,
                time,
                LocalDateTime.now());
    }
}

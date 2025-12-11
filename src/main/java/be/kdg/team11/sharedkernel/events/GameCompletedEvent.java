package be.kdg.team11.sharedkernel.events;

import java.time.Duration;
import java.util.UUID;

public record GameCompletedEvent(
        UUID playerId,
        UUID gameId,
        long totalGamesPlayed,
        long totalWins,
        long totalFriends,
        Duration bestRecordTime
) implements DomainEvent {
}


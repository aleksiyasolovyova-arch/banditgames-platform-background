package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

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


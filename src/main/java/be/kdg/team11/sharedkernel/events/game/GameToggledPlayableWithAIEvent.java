package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

public record GameToggledPlayableWithAIEvent(
        UUID gameId,
        boolean playableWithAI,
        LocalDate eventPit
)implements DomainEvent {
    public GameToggledPlayableWithAIEvent(UUID gameId, boolean playableWithAI) {
        this(gameId, playableWithAI, LocalDate.now());
    }
}

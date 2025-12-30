package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameToggledPlayableWithAIEvent(
        UUID gameId,
        boolean playableWithAI,
        LocalDateTime eventPit
) implements DomainEvent {
    public GameToggledPlayableWithAIEvent(UUID gameId, boolean playableWithAI) {
        this(gameId, playableWithAI, LocalDateTime.now());
    }
}

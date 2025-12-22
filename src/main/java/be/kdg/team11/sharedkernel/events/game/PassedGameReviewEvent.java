package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PassedGameReviewEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<RuleRecord> rules,
        boolean playableWithAI
) implements DomainEvent {
    public PassedGameReviewEvent(
            UUID gameId,
            String name,
            String description,
            String pictureUrl,
            String gameUrl,
            String gameCreatorName,
            List<RuleRecord> rules,
            boolean playableWithAI
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                name,
                description,
                pictureUrl,
                gameUrl,
                gameCreatorName,
                rules,
                playableWithAI
        );
    }

    public record RuleRecord(
            String description
    ) {

        public static GameRegisteredEvent.RuleRecord of(String description) {
            return new GameRegisteredEvent.RuleRecord(description);
        }
    }
}

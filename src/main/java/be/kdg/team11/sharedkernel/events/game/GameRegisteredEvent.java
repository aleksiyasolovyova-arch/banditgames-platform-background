package be.kdg.team11.sharedkernel.events.game;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameRegisteredEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID gameId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<RuleRecord> rules,
        List<GameAchievementRecord> achievements
)implements DomainEvent {

    public GameRegisteredEvent(
            UUID gameId,
            String name,
            String description,
            BigDecimal price,
            String pictureUrl,
            String gameUrl,
            String gameCreatorName,
            List<RuleRecord> rules,
            List<GameAchievementRecord> achievements
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                name,
                description,
                price,
                pictureUrl,
                gameUrl,
                gameCreatorName,
                rules,
                achievements
        );
    }

    public record RuleRecord(
            String description
    ) {

        public static RuleRecord of(String description) {
            return new RuleRecord(description);
        }
    }

    public record GameAchievementRecord(
            String code,
            String description
    ) {

        public static GameAchievementRecord of(String code, String description) {
            return new GameAchievementRecord(code, description);
        }
    }
}

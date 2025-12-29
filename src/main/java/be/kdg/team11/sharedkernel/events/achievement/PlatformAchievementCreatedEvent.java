package be.kdg.team11.sharedkernel.events.achievement;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlatformAchievementCreatedEvent(
        UUID uuid,
        LocalDateTime eventPit,
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) implements DomainEvent {
    public PlatformAchievementCreatedEvent(
            UUID achievementId,
            String name,
            String description,
            String pictureUrl,
            String type,
            long requiredValue
    ) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                achievementId,
                name,
                description,
                pictureUrl,
                type,
                requiredValue
        );
    }
}

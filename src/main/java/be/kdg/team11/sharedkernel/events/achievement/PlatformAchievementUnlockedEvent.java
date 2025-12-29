package be.kdg.team11.sharedkernel.events.achievement;

import be.kdg.team11.sharedkernel.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlatformAchievementUnlockedEvent(
        UUID playerId,
        UUID achievementId,
        LocalDateTime eventPit
) implements DomainEvent {
    public PlatformAchievementUnlockedEvent(UUID playerId, UUID achievementId) {
        this(playerId, achievementId, LocalDateTime.now());
    }
}

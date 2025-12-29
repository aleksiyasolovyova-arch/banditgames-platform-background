package be.kdg.team11.player.domain.player;

import java.util.UUID;

public record PlatformAchievementId(
        UUID achievementId
) {

    public static PlatformAchievementId create() {
        return new PlatformAchievementId(UUID.randomUUID());
    }

    public static PlatformAchievementId of(UUID achievementId) {
        return new PlatformAchievementId(achievementId);
    }
}

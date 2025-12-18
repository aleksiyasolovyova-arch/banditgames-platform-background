package be.kdg.team11.player.domain.player;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {

    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }

    public static AchievementId of(UUID achievementId) {
        return new AchievementId(achievementId);
    }
}

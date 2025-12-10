package team11.platform_backend.game.domain.achievement;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }
}

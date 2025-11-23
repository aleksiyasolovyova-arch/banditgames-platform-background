package team11.platform_backend.player.domain.player;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId createAchievementId() {
        return new AchievementId(UUID.randomUUID());
    }
}

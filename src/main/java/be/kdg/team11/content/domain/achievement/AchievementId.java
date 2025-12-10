package be.kdg.team11.content.domain.achievement;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }
}

package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.achievement.exeptions.AchievementNotFoundException;
import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId create() {
        return new AchievementId(UUID.randomUUID());
    }

    public static AchievementId of(UUID uuid) {
        return new AchievementId(uuid);
    }

    public static AchievementNotFoundException notFound(AchievementId achievementId) {
        return new AchievementNotFoundException(
                String.format("Achievement not found with ID: %s", achievementId.achievementId())
        );
    }

}

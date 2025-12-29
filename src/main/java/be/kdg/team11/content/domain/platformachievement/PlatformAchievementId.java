package be.kdg.team11.content.domain.platformachievement;

import be.kdg.team11.content.domain.platformachievement.exeptions.PlatformAchievementNotFoundException;
import java.util.UUID;

public record PlatformAchievementId(
        UUID achievementId
) {
    public static PlatformAchievementId create() {
        return new PlatformAchievementId(UUID.randomUUID());
    }

    public static PlatformAchievementId of(UUID uuid) {
        return new PlatformAchievementId(uuid);
    }

    public static PlatformAchievementNotFoundException notFound(PlatformAchievementId platformAchievementId) {
        return new PlatformAchievementNotFoundException(
                String.format("Achievement not found with ID: %s", platformAchievementId.achievementId())
        );
    }

}

package be.kdg.team11.readmodel.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AchievementDto (
        // Game or platform?
        String achievementType,

        // Shared achievement fields
        String achievementDescription,
        boolean unlocked,
        LocalDateTime unlockedAt,

        UUID gameId,
        String gameName,
        String gameAchievementCode,

        UUID platformAchievementId,
        String platformAchievementName,
        String platformAchievementPictureUrl,
        // What kind of platform achievement?
        String platformAchievementType,
        long platformAchievementRequiredValue,
        long platformAchievementCurrentValue

        ) {
}

package be.kdg.team11.readmodel.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AchievementPlayerResponseModelDto(
        UUID achievementId,
        String achievementType,
        String achievementDescription,
        boolean unlocked,
        LocalDateTime unlockedAt,

        UUID platformAchievementId,
        String platformAchievementName,
        String platformAchievementPictureUrl,
        String platformAchievementType,
        Long platformAchievementRequiredValue,
        Long platformAchievementCurrentValue,

        UUID gameId,
        String gameName,
        String gameAchievementCode

) implements AchievementModelDto {
}

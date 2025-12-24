package be.kdg.team11.readmodel.controller.dto.achievement;

import java.util.UUID;

public record AdminAchievementModelDto(
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String type,
        Long requiredValue
) implements AchievementModelDto {
}

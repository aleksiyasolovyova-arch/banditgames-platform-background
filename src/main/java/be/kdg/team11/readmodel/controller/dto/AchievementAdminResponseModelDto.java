package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record AchievementAdminResponseModelDto(
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String type,
        Long requiredValue
) implements AchievementModelDto {
}

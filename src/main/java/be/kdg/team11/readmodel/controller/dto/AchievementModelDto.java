package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record AchievementModelDto(
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {
}

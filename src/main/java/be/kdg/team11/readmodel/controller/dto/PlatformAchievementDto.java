package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record PlatformAchievementDto(
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {
}

package be.kdg.team11.content.adapter.in.response;

import java.util.UUID;

public record PlatformAchievementDto(
        UUID platformAchievementId,
        String name,
        String description,
        String pictureUrl,
        String platformAchievementType,
        long requiredValue
) {
}

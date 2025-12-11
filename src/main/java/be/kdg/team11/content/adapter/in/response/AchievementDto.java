package be.kdg.team11.content.adapter.in.response;
import java.util.UUID;

public record AchievementDto(
        UUID achievementId,
        String name,
        String description,
        String pictureUrl,
        String achievementType,
        long requiredValue
) {}

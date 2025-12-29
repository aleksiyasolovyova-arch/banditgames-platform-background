package be.kdg.team11.readmodel.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AchievementsDto(
        List<PlatformAchievementDto> platformAchievements,
        List<GameAchievementDto> gameAchievements
) {
    public record PlatformAchievementDto(
            UUID achievementId,
            String name,
            String description,
            String pictureUrl,
            String type,
            long requiredValue,
            LocalDateTime unlockedAt
    ) {}

    public record GameAchievementDto(
            UUID gameId,
            String gameName,
            String pictureUrl,
            String code,
            String description,
            LocalDateTime unlockedAt
    ) {}
}


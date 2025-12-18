package be.kdg.team11.content.port.in;

public record CreateAchievementCommand(
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {}

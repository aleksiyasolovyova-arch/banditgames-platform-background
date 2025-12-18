package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementTypeException;

public record CreateAchievementCommand(
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {}

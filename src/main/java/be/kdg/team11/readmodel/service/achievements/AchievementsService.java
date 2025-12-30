package be.kdg.team11.readmodel.service.achievements;

import be.kdg.team11.readmodel.controller.dto.AchievementsDto;

import java.util.UUID;

public interface AchievementsService {
    AchievementsDto getAchievements(UUID playerId);
}

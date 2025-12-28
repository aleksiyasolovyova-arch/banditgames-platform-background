package be.kdg.team11.readmodel.service.playerachievements;

import be.kdg.team11.readmodel.controller.dto.PlayerAchievementsDto;

import java.util.UUID;

public interface PlayerAchievementsService {
    PlayerAchievementsDto getPlayerAchievements(UUID playerId);
}

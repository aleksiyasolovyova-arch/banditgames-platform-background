package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.AchievementPlayerResponseDto;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;

import java.util.List;
import java.util.UUID;

public interface AchievementModelService {

    void project(AchievementCreatedEvent event);

    List<AchievementPlayerResponseDto> getPlayerAchievements(UUID playerId);
}

package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.achievement.AchievementModelDto;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;

import java.util.List;
import java.util.UUID;

public interface AchievementModelService {
    void project(AchievementCreatedEvent event);

   List<? extends AchievementModelDto> getPlayerAchievements(UUID playerId);
   List<? extends AchievementModelDto> getAllPlatformAchievements();
}

package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.readmodel.controller.dto.AchievementModelDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;

import java.util.List;
import java.util.UUID;

public interface AchievementModelService {

    void project(AchievementCreatedEvent event);

   List<? extends AchievementModelDto> getPlayerAchievements(UUID playerId);
   List<? extends AchievementModelDto> getAllPlatformAchievements();
}

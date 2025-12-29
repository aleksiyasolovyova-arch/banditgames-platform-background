package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.AchievementModelDto;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;

import java.util.List;

public interface AchievementModelService {
    void project(PlatformAchievementCreatedEvent event);
    List<AchievementModelDto> getAll();
}

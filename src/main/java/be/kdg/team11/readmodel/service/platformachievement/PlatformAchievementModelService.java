package be.kdg.team11.readmodel.service.platformachievement;

import be.kdg.team11.readmodel.controller.dto.PlatformAchievementDto;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;

import java.util.List;

public interface PlatformAchievementModelService {
    void project(PlatformAchievementCreatedEvent event);
    List<PlatformAchievementDto> getAll();
}

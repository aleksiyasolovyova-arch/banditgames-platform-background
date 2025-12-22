package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;

public interface AchievementModelService {

    void project(AchievementCreatedEvent event);
}

package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.achievement.Achievement;

public interface UpdateAchievementPort {
    Achievement updateAchievement(UpdateAchievementCommand command);
}
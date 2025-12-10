package be.kdg.team11.game.port.in;

import be.kdg.team11.game.domain.achievement.Achievement;

public interface CreateAchievementPort {
    Achievement createAchievement(CreateAchievementCommand command);
}

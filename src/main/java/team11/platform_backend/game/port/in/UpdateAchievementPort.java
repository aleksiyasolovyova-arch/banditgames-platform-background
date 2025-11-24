package team11.platform_backend.game.port.in;

import team11.platform_backend.game.domain.achievement.Achievement;

public interface UpdateAchievementPort {
    Achievement updateAchievement(UpdateAchievementCommand command);
}
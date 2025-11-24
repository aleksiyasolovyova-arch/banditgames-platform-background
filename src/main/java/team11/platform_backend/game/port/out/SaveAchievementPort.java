package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.achievement.Achievement;

public interface SaveAchievementPort {
    Achievement save(Achievement achievement);
}

package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementId;

import java.util.Optional;

public interface LoadAchievementPort {
    Optional<Achievement> loadBy(AchievementId achievementId);
}

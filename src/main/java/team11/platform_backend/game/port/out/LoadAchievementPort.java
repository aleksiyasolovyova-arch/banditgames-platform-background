package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.game.GameId;

import java.util.List;
import java.util.Optional;

public interface LoadAchievementPort {
    List<Achievement> findByGameId(GameId gameId);
    Optional<Achievement> findById(AchievementId achievementId);
}

package team11.platform_backend.game.port.out;

import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.game.GameId;

import java.util.List;

public interface LoadAchievementPort {
    List<Achievement> findByGameId(GameId gameId);
}

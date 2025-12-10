package be.kdg.team11.game.port.out;

import be.kdg.team11.game.domain.achievement.Achievement;
import be.kdg.team11.game.domain.achievement.AchievementId;

import java.util.Optional;

public interface LoadAchievementPort {
    Optional<Achievement> loadBy(AchievementId achievementId);
}

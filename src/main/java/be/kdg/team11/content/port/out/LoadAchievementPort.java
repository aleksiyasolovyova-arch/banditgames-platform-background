package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.achievement.AchievementId;

import java.util.Optional;

public interface LoadAchievementPort {
    Optional<Achievement> loadBy(AchievementId achievementId);
}

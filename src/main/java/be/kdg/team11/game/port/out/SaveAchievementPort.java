package be.kdg.team11.game.port.out;

import be.kdg.team11.game.domain.achievement.Achievement;

public interface SaveAchievementPort {
    Achievement save(Achievement achievement);
}

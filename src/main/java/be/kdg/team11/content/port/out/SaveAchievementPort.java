package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.achievement.Achievement;

public interface SaveAchievementPort {
    Achievement save(Achievement achievement);
}

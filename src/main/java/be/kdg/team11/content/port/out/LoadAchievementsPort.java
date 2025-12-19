package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.game.GameId;

import java.util.List;

public interface LoadAchievementsPort {
    List<Achievement> loadAll();
}

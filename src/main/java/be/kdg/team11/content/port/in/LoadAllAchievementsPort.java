package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.achievement.Achievement;

import java.util.List;

public interface LoadAllAchievementsPort {
    List<Achievement> loadAll();
}

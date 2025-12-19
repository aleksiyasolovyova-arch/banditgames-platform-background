package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.LoadAllAchievementsPort;
import be.kdg.team11.content.port.out.LoadAchievementPort;
import be.kdg.team11.content.port.out.LoadAchievementsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class LoadAllAchievementsUseCaseImpl implements LoadAllAchievementsPort {
    private final LoadAchievementsPort loadAchievementsPort;

    public LoadAllAchievementsUseCaseImpl(LoadAchievementsPort loadAchievementsPort) {
        this.loadAchievementsPort = loadAchievementsPort;
    }

    @Override
    public List<Achievement> loadAll() {
        return loadAchievementsPort.loadAll();
    }

}

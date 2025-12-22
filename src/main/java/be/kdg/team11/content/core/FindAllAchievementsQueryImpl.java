package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.FindAllAchievementsQueryPort;
import be.kdg.team11.content.port.out.LoadAchievementsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FindAllAchievementsQueryImpl implements FindAllAchievementsQueryPort {
    private final LoadAchievementsPort loadAchievementsPort;
    public FindAllAchievementsQueryImpl(LoadAchievementsPort loadAchievementsPort) {
        this.loadAchievementsPort = loadAchievementsPort;
    }

    @Override
    public List<Achievement> findAll() {
        return loadAchievementsPort.loadAll();
    }
}

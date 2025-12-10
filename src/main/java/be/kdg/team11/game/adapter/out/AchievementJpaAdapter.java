package be.kdg.team11.game.adapter.out;

import org.springframework.stereotype.Component;
import be.kdg.team11.game.adapter.out.jpa.AchievementJpaEntity;
import be.kdg.team11.game.adapter.out.jpa.AchievementJpaRepository;
import be.kdg.team11.game.adapter.out.mapper.AchievementJpaMapper;
import be.kdg.team11.game.domain.achievement.Achievement;
import be.kdg.team11.game.domain.achievement.AchievementId;
import be.kdg.team11.game.port.out.LoadAchievementPort;
import be.kdg.team11.game.port.out.SaveAchievementPort;

import java.util.Optional;

@Component
public class AchievementJpaAdapter implements SaveAchievementPort, LoadAchievementPort {
    private final AchievementJpaRepository achievementJpaRepository;
    private final AchievementJpaMapper achievementJpaMapper;

    public AchievementJpaAdapter(AchievementJpaRepository achievementJpaRepository,
                                 AchievementJpaMapper achievementJpaMapper) {
        this.achievementJpaRepository = achievementJpaRepository;
        this.achievementJpaMapper = achievementJpaMapper;
    }

    @Override
    public Achievement save(Achievement achievement) {
        AchievementJpaEntity entity = achievementJpaMapper.toJpaEntity(achievement);
        AchievementJpaEntity saved = achievementJpaRepository.save(entity);
        return achievementJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Achievement> loadBy(AchievementId achievementId) {
        return achievementJpaRepository.findById(achievementId.achievementId())
                .map(achievementJpaMapper::toDomain);
    }

}

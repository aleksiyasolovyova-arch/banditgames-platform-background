package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.adapter.out.jpa.AchievementJpaEntity;
import be.kdg.team11.content.adapter.out.jpa.AchievementJpaRepository;
import be.kdg.team11.content.adapter.out.mapper.AchievementJpaMapper;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.achievement.AchievementId;
import be.kdg.team11.content.port.out.LoadAchievementPort;
import be.kdg.team11.content.port.out.SaveAchievementPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AchievementJpaAdapter implements SaveAchievementPort, LoadAchievementPort{
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

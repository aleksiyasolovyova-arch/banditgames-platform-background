package team11.platform_backend.game.adapter.out;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.AchievementJpaEntity;
import team11.platform_backend.game.adapter.out.jpa.AchievementJpaRepository;
import team11.platform_backend.game.adapter.out.mapper.AchievementJpaMapper;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.port.out.LoadAchievementPort;
import team11.platform_backend.game.port.out.SaveAchievementPort;

import java.util.List;
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
    public List<Achievement> findByGameId(GameId gameId) {
        return achievementJpaRepository.findByGameId(gameId.gameId()).stream()
                .map(achievementJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Achievement> findById(AchievementId achievementId) {
        return achievementJpaRepository.findById(achievementId.achievementId())
                .map(achievementJpaMapper::toDomain);
    }

}

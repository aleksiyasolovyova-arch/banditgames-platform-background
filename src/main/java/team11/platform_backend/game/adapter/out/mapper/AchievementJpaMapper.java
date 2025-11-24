package team11.platform_backend.game.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.AchievementJpaEntity;
import team11.platform_backend.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.sharedkernel.valueobjects.Url;

@Component
public class AchievementJpaMapper {

    public AchievementJpaEntity toJpaEntity(Achievement achievement) {
        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievement.getAchievementId().achievementId());
        entity.setGameId(achievement.getGameId().gameId());
        entity.setAchievementName(achievement.getAchievementName());
        entity.setAchievementDescription(achievement.getAchievementDescription());
        entity.setPictureUrl(achievement.getPictureUrl().value());
        entity.setAchievementType(achievement.getAchievementThreshold().achievementType());
        entity.setThreshold(achievement.getAchievementThreshold().threshold());
        return entity;
    }

    public Achievement toDomain(AchievementJpaEntity entity) {
        return new Achievement(
                new AchievementId(entity.getAchievementId()),
                new GameId(entity.getGameId()),
                entity.getAchievementName(),
                entity.getAchievementDescription(),
                new Url(entity.getPictureUrl()),
                new AchievementThreshold(
                        entity.getAchievementType(),
                        entity.getThreshold()
                )
        );
    }
}

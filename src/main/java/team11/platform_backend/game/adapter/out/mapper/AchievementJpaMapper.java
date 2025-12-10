package team11.platform_backend.game.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.AchievementJpaEntity;
import team11.platform_backend.game.domain.Url;
import team11.platform_backend.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.achievement.Achievement;

@Component
public class AchievementJpaMapper {

    public AchievementJpaEntity toJpaEntity(Achievement achievement) {
        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievement.getAchievementId().achievementId());
        entity.setName(achievement.getName());
        entity.setDescription(achievement.getDescription());
        entity.setPictureUrl(achievement.getPictureUrl().value());
        entity.setType(achievement.getType());
        entity.setRequiredValue(achievement.getRequiredValue());
        return entity;
    }

    public Achievement toDomain(AchievementJpaEntity entity) {
        return new Achievement(
                new AchievementId(entity.getAchievementId()),
                entity.getName(),
                entity.getDescription(),
                new Url(entity.getPictureUrl()),
                entity.getType(),
                entity.getRequiredValue()
        );
    }
}

package be.kdg.team11.content.adapter.out.mapper;

import be.kdg.team11.content.adapter.out.jpa.AchievementJpaEntity;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.achievement.AchievementId;
import org.springframework.stereotype.Component;

@Component
public class AchievementJpaMapper {

    public AchievementJpaEntity toJpaEntity(Achievement achievement) {
        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievement.getAchievementId().achievementId());
        entity.setName(achievement.getName());
        entity.setDescription(achievement.getDescription());
        entity.setPictureUrl(achievement.getPictureUrl());
        entity.setType(achievement.getType());
        entity.setRequiredValue(achievement.getRequiredValue());
        return entity;
    }

    public Achievement toDomain(AchievementJpaEntity entity) {
        return new Achievement(
                new AchievementId(entity.getAchievementId()),
                entity.getName(),
                entity.getDescription(),
                entity.getPictureUrl(),
                entity.getType(),
                entity.getRequiredValue()
        );
    }
}

package be.kdg.team11.content.adapter.out.mapper;

import be.kdg.team11.content.adapter.out.jpa.PlatformAchievementJpaEntity;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievementId;
import org.springframework.stereotype.Component;

@Component
public class PlatformAchievementJpaMapper {

    public PlatformAchievementJpaEntity toJpaEntity(PlatformAchievement platformAchievement) {
        PlatformAchievementJpaEntity entity = new PlatformAchievementJpaEntity();
        entity.setPlatformAchievementId(platformAchievement.getAchievementId().achievementId());
        entity.setName(platformAchievement.getName());
        entity.setDescription(platformAchievement.getDescription());
        entity.setPictureUrl(platformAchievement.getPictureUrl());
        entity.setType(platformAchievement.getType());
        entity.setRequiredValue(platformAchievement.getRequiredValue());
        return entity;
    }

    public PlatformAchievement toDomain(PlatformAchievementJpaEntity entity) {
        return new PlatformAchievement(
                new PlatformAchievementId(entity.getPlatformAchievementId()),
                entity.getName(),
                entity.getDescription(),
                entity.getPictureUrl(),
                entity.getType(),
                entity.getRequiredValue()
        );
    }
}

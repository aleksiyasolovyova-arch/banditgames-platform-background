package team11.platform_backend.game.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.AchievementJpaEntity;
import team11.platform_backend.game.domain.achievement.*;
import team11.platform_backend.game.domain.Url;

import java.time.Duration;

@Component
public class AchievementJpaMapper {

    public AchievementJpaEntity toJpaEntity(Achievement achievement) {
        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievement.getAchievementId().achievementId());
        entity.setName(achievement.getName());
        entity.setDescription(achievement.getDescription());
        entity.setPictureUrl(achievement.getPictureUrl().value());

        Threshold threshold = achievement.getThreshold();

        // Map threshold based on its type
        if (threshold instanceof CountThreshold(long value, AchievementType type)) {
            entity.setAchievementType(type);
            entity.setThresholdCountValue(value);
            entity.setThresholdDurationSeconds(null);
        } else if (threshold instanceof TimeThreshold(Duration value)) {
            entity.setAchievementType(AchievementType.RECORD_TIME);
            entity.setThresholdCountValue(null);
            entity.setThresholdDurationSeconds(value.toSeconds());
        }

        return entity;
    }

    public Achievement toDomain(AchievementJpaEntity entity) {
        // Reconstruct the Threshold based on achievement type
        Threshold threshold;
        if (entity.getAchievementType() == AchievementType.RECORD_TIME) {
            Duration duration = Duration.ofSeconds(entity.getThresholdDurationSeconds());
            threshold = new TimeThreshold(duration);
        } else {
            // PLAY_COUNT, WIN_COUNT, FRIEND_COUNT
            threshold = new CountThreshold(
                    entity.getThresholdCountValue(),
                    entity.getAchievementType()
            );
        }

        return new Achievement(
                new AchievementId(entity.getAchievementId()),
                entity.getName(),
                entity.getDescription(),
                new Url(entity.getPictureUrl()),
                threshold
        );
    }
}
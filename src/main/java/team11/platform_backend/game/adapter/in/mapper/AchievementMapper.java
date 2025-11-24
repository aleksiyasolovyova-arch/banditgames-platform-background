package team11.platform_backend.game.adapter.in.mapper;
import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.in.request.CreateAchievementRequest;
import team11.platform_backend.game.adapter.in.request.UpdateAchievementRequest;
import team11.platform_backend.game.adapter.in.response.AchievementDto;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.port.in.CreateAchievementCommand;
import team11.platform_backend.game.port.in.UpdateAchievementCommand;

import java.util.UUID;

@Component
public class AchievementMapper {
    public CreateAchievementCommand toCommand(CreateAchievementRequest request) {
        return new CreateAchievementCommand(
                request.gameId(),
                request.achievementName(),
                request.achievementDescription(),
                request.pictureUrl(),
                request.achievementType(),
                request.threshold()
        );
    }

    public UpdateAchievementCommand toUpdateCommand(UUID achievementId, UpdateAchievementRequest request) {
        return new UpdateAchievementCommand(
                achievementId,
                request.achievementName(),
                request.achievementDescription(),
                request.pictureUrl(),
                request.achievementType(),
                request.threshold()
        );
    }

    public AchievementDto toResponse(Achievement achievement) {
        return new AchievementDto(
                achievement.getAchievementId().achievementId(),
                achievement.getGameId().gameId(),
                achievement.getAchievementName(),
                achievement.getAchievementDescription(),
                achievement.getPictureUrl().value(),
                achievement.getAchievementThreshold().achievementType().name(),
                achievement.getAchievementThreshold().threshold()
        );
    }


}

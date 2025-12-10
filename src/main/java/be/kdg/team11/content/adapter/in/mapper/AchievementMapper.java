package be.kdg.team11.content.adapter.in.mapper;
import org.springframework.stereotype.Component;
import be.kdg.team11.content.adapter.in.request.CreateAchievementRequest;
import be.kdg.team11.content.adapter.in.request.UpdateAchievementRequest;
import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementCommand;

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

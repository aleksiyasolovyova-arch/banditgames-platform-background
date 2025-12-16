package be.kdg.team11.content.adapter.in.mapper;

import be.kdg.team11.content.adapter.in.request.CreateAchievementRequest;
import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementCommand;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {
    public CreateAchievementCommand toCommand(CreateAchievementRequest request) {
        return new CreateAchievementCommand(
                request.achievementName(),
                request.description(),
                request.pictureUrl(),
                request.achievementType().name(),
                request.requiredValue()
        );
    }


    public AchievementDto toResponse(Achievement achievement) {
        return new AchievementDto(
                achievement.getAchievementId().achievementId(),
                achievement.getName(),
                achievement.getDescription(),
                achievement.getPictureUrl().value(),
                achievement.getType().name(),
                achievement.getRequiredValue()
        );
    }


}

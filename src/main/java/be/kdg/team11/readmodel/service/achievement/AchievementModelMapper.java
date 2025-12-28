package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.AchievementModelDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import org.springframework.stereotype.Component;

@Component
public class AchievementModelMapper {
    public AchievementModelDto toAdminAchievementModelDto(AchievementModel achievement) {
        return new AchievementModelDto(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getDescription(),
                achievement.getPictureUrl(),
                achievement.getType(),
                achievement.getRequiredValue()
        );
    }
}

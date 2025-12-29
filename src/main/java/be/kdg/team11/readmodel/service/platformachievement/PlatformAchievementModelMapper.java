package be.kdg.team11.readmodel.service.platformachievement;

import be.kdg.team11.readmodel.controller.dto.PlatformAchievementDto;
import be.kdg.team11.readmodel.models.PlatformAchievementModel;
import org.springframework.stereotype.Component;

@Component
public class PlatformAchievementModelMapper {
    public PlatformAchievementDto toAdminPlatformAchievementModelDto(PlatformAchievementModel platformAchievement) {
        return new PlatformAchievementDto(
                platformAchievement.getAchievementId(),
                platformAchievement.getName(),
                platformAchievement.getDescription(),
                platformAchievement.getPictureUrl(),
                platformAchievement.getType(),
                platformAchievement.getRequiredValue()
        );
    }
}

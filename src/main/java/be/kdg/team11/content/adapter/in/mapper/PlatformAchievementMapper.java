package be.kdg.team11.content.adapter.in.mapper;

import be.kdg.team11.content.adapter.in.request.CreatePlatformAchievementRequest;
import be.kdg.team11.content.adapter.in.response.PlatformAchievementDto;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.port.in.CreatePlatformAchievementCommand;
import org.springframework.stereotype.Component;

@Component
public class PlatformAchievementMapper {
    public CreatePlatformAchievementCommand toCommand(CreatePlatformAchievementRequest request) {
        return new CreatePlatformAchievementCommand(
                request.platformAchievementName(),
                request.description(),
                request.pictureUrl(),
                request.platformAchievementType().name(),
                request.requiredValue()
        );
    }


    public PlatformAchievementDto toResponse(PlatformAchievement platformAchievement) {
        return new PlatformAchievementDto(
                platformAchievement.getAchievementId().achievementId(),
                platformAchievement.getName(),
                platformAchievement.getDescription(),
                platformAchievement.getPictureUrl(),
                platformAchievement.getType().name(),
                platformAchievement.getRequiredValue()
        );
    }


}

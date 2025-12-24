package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.achievement.AdminAchievementModelDto;
import be.kdg.team11.readmodel.controller.dto.achievement.PlayerAchievementModelDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.UnlockedAchievementModel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class AchievementModelMapper {
    public PlayerAchievementModelDto toAchievementPlayerResponseDto(
            AchievementModel achievement,
            Set<UUID> unlockedAchievements,
            Map<UUID, Long> playerStatistics) {

        UUID achievementId = achievement.getAchievementIdPK();
        boolean isUnlocked = unlockedAchievements.contains(achievementId);
        UnlockedAchievementModel unlockedData = null;

        // Determine if PLATFORM or GAME achievement
        boolean isPlatformAchievement = achievement.getGameId() == null;
        String achievementType = isPlatformAchievement ? "PLATFORM" : "GAME";

        // Shared fields
        UUID dtoAchievementId = achievementId;
        String dtoDescription = achievement.getDescription();

        // For platform achievements
        UUID dtoPlatformAchievementId = isPlatformAchievement ? achievementId : null;
        String dtoPlatformName = isPlatformAchievement ? achievement.getName() : null;
        String dtoPlatformPicture = isPlatformAchievement ? achievement.getPictureUrl() : null;
        String dtoPlatformType = isPlatformAchievement ?
                (achievement.getType() != null ? achievement.getType().toString() : null) : null;
        Long dtoPlatformRequired = isPlatformAchievement ? achievement.getRequiredValue() : null;
        Long dtoPlatformCurrent = isPlatformAchievement ?
                playerStatistics.getOrDefault(achievementId, 0L) : null;

        // For game achievements
        UUID dtoGameId = !isPlatformAchievement ? achievement.getGameId() : null;
        String dtoGameName = !isPlatformAchievement ? achievement.getGameName() : null;
        String dtoGameCode = !isPlatformAchievement ? achievement.getAchievementCode() : null;

        return new PlayerAchievementModelDto(
                dtoAchievementId,
                achievementType,
                dtoDescription,
                isUnlocked,
                null, // unlockedAt - populated by service if unlocked

                // Platform fields
                dtoPlatformAchievementId,
                dtoPlatformName,
                dtoPlatformPicture,
                dtoPlatformType,
                dtoPlatformRequired,
                dtoPlatformCurrent,

                // Game fields
                dtoGameId,
                dtoGameName,
                dtoGameCode
        );
    }

    public PlayerAchievementModelDto toAchievementPlayerResponseDto(
            AchievementModel achievement,
            Set<UUID> unlockedAchievements,
            Map<UUID, Long> playerStatistics,
            Map<UUID, UnlockedAchievementModel> unlockedDataMap) {

        UUID achievementId = achievement.getAchievementIdPK();
        UnlockedAchievementModel unlockedData = unlockedDataMap.get(achievementId);

        UUID dtoAchievementId = achievementId;
        boolean isUnlocked = unlockedAchievements.contains(achievementId);
        String dtoDescription = achievement.getDescription();

        // Determine if PLATFORM or GAME achievement
        boolean isPlatformAchievement = achievement.getGameId() == null;
        String achievementType = isPlatformAchievement ? "PLATFORM" : "GAME";

        // For platform achievements
        UUID dtoPlatformAchievementId = isPlatformAchievement ? achievementId : null;
        String dtoPlatformName = isPlatformAchievement ? achievement.getName() : null;
        String dtoPlatformPicture = isPlatformAchievement ? achievement.getPictureUrl() : null;
        String dtoPlatformType = isPlatformAchievement ?
                (achievement.getType() != null ? achievement.getType().toString() : null) : null;
        Long dtoPlatformRequired = isPlatformAchievement ? achievement.getRequiredValue() : null;
        Long dtoPlatformCurrent = isPlatformAchievement ?
                playerStatistics.getOrDefault(achievementId, 0L) : null;

        // For game achievements
        UUID dtoGameId = !isPlatformAchievement ? achievement.getGameId() : null;
        String dtoGameName = !isPlatformAchievement ? achievement.getGameName() : null;
        String dtoGameCode = !isPlatformAchievement ? achievement.getAchievementCode() : null;

        return new PlayerAchievementModelDto(
                dtoAchievementId,
                achievementType,
                dtoDescription,
                isUnlocked,
                isUnlocked ? unlockedData.getUnlockedAt() : null,

                // Platform fields
                dtoPlatformAchievementId,
                dtoPlatformName,
                dtoPlatformPicture,
                dtoPlatformType,
                dtoPlatformRequired,
                dtoPlatformCurrent,

                // Game fields
                dtoGameId,
                dtoGameName,
                dtoGameCode
        );
    }

    public AdminAchievementModelDto toAchievementAdminResponseDto(AchievementModel achievement) {
        return new AdminAchievementModelDto(
                achievement.getPlatformAchievementId(),
                achievement.getName(),
                achievement.getDescription(),
                achievement.getPictureUrl(),
                achievement.getType() != null ? achievement.getType().toString() : null,
                achievement.getRequiredValue()
        );
    }
}

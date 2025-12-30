package be.kdg.team11.readmodel.service.achievements;

import be.kdg.team11.readmodel.controller.dto.AchievementsDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.PlatformAchievementModel;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.PlatformAchievementModelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AchievementsServiceImpl implements AchievementsService {
    private final AchievementModelRepository achievementModelRepository;
    private final PlatformAchievementModelRepository platformAchievementModelRepository;
    private final GameModelRepository gameModelRepository;

    public AchievementsServiceImpl(AchievementModelRepository achievementModelRepository, PlatformAchievementModelRepository platformAchievementModelRepository, GameModelRepository gameModelRepository) {
        this.achievementModelRepository = achievementModelRepository;
        this.platformAchievementModelRepository = platformAchievementModelRepository;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public AchievementsDto getAchievements(UUID playerId) {
        List<AchievementModel> unlockedAchievements = achievementModelRepository.findByPlayerId(playerId);
        List<GameModel> games = gameModelRepository.findAll();
        List<PlatformAchievementModel> platformAchievements = platformAchievementModelRepository.findAll();

        List<AchievementsDto.PlatformAchievementDto> platformAchievementDTOS = platformAchievements.stream()
                .map(platformAchievement -> {
                    LocalDateTime unlockedAt = unlockedAchievements.stream()
                            .filter(unlocked -> unlocked.getAchievementId() != null &&
                                    unlocked.getAchievementId().equals(platformAchievement.getAchievementId()))
                            .findFirst()
                            .map(AchievementModel::getUnlockedAt)
                            .orElse(null);

                    return new AchievementsDto.PlatformAchievementDto(
                            platformAchievement.getAchievementId(),
                            platformAchievement.getName(),
                            platformAchievement.getDescription(),
                            platformAchievement.getPictureUrl(),
                            platformAchievement.getType(),
                            platformAchievement.getRequiredValue(),
                            unlockedAt
                    );
                })
                .toList();

        List<AchievementsDto.GameAchievementDto> gameAchievementDTOs = games.stream()
                .flatMap(game -> game.getAchievements().stream()
                        .map(gameAchievement -> {
                            LocalDateTime unlockedAt = unlockedAchievements.stream()
                                    .filter(unlocked -> unlocked.getGameId() != null &&
                                            unlocked.getGameId().equals(game.getGameId()) &&
                                            unlocked.getGameAchievementCode() != null &&
                                            unlocked.getGameAchievementCode().equals(gameAchievement.getCode()))
                                    .findFirst()
                                    .map(AchievementModel::getUnlockedAt)
                                    .orElse(null);

                            return new AchievementsDto.GameAchievementDto(
                                    game.getGameId(),
                                    game.getName(),
                                    game.getPictureUrl(),
                                    gameAchievement.getCode(),
                                    gameAchievement.getDescription(),
                                    unlockedAt
                            );
                        })
                )
                .toList();

        return new AchievementsDto(platformAchievementDTOS, gameAchievementDTOs);
    }
}

package be.kdg.team11.readmodel.service.playerachievements;

import be.kdg.team11.readmodel.controller.dto.PlayerAchievementsDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.UnlockedAchievementModel;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.UnlockedAchievementModelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerAchievementsServiceImpl implements PlayerAchievementsService{
    private final UnlockedAchievementModelRepository unlockedAchievementModelRepository;
    private final AchievementModelRepository achievementModelRepository;
    private final GameModelRepository gameModelRepository;

    public PlayerAchievementsServiceImpl(UnlockedAchievementModelRepository unlockedAchievementModelRepository, AchievementModelRepository achievementModelRepository, GameModelRepository gameModelRepository) {
        this.unlockedAchievementModelRepository = unlockedAchievementModelRepository;
        this.achievementModelRepository = achievementModelRepository;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public PlayerAchievementsDto getPlayerAchievements(UUID playerId) {
        List<UnlockedAchievementModel> unlockedAchievements = unlockedAchievementModelRepository.findByPlayerId(playerId);
        List<GameModel> games = gameModelRepository.findAll();
        List<AchievementModel> achievements = achievementModelRepository.findAll();

        List<PlayerAchievementsDto.AchievementDto> achievementDTOs = achievements.stream()
                .map(achievement -> {
                    LocalDateTime unlockedAt = unlockedAchievements.stream()
                            .filter(unlocked -> unlocked.getAchievementId() != null &&
                                    unlocked.getAchievementId().equals(achievement.getAchievementId()))
                            .findFirst()
                            .map(UnlockedAchievementModel::getUnlockedAt)
                            .orElse(null);

                    return new PlayerAchievementsDto.AchievementDto(
                            achievement.getAchievementId(),
                            achievement.getName(),
                            achievement.getDescription(),
                            achievement.getPictureUrl(),
                            achievement.getType(),
                            achievement.getRequiredValue(),
                            unlockedAt
                    );
                })
                .toList();

        List<PlayerAchievementsDto.GameAchievementDto> gameAchievementDTOs = games.stream()
                .flatMap(game -> game.getAchievements().stream()
                        .map(gameAchievement -> {
                            LocalDateTime unlockedAt = unlockedAchievements.stream()
                                    .filter(unlocked -> unlocked.getGameId() != null &&
                                            unlocked.getGameId().equals(game.getGameId()) &&
                                            unlocked.getGameAchievementCode() != null &&
                                            unlocked.getGameAchievementCode().equals(gameAchievement.getCode()))
                                    .findFirst()
                                    .map(UnlockedAchievementModel::getUnlockedAt)
                                    .orElse(null);

                            return new PlayerAchievementsDto.GameAchievementDto(
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

        return new PlayerAchievementsDto(achievementDTOs, gameAchievementDTOs);
    }
}

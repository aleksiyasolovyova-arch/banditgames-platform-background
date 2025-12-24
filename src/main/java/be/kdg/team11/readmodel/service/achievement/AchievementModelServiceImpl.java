package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.achievement.AchievementModelDto;
import be.kdg.team11.readmodel.controller.dto.achievement.PlayerAchievementModelDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.AchievementModelType;
import be.kdg.team11.readmodel.models.UnlockedAchievementModel;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.readmodel.repository.UnlockedAchievementModelRepository;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AchievementModelServiceImpl implements AchievementModelService{
    private final AchievementModelRepository achievementModelRepository;
    private final UnlockedAchievementModelRepository unlockedAchievementModelRepository;
    private final AchievementModelMapper achievementModelMapper;
    private final PlayerModelRepository playerModelRepository;
    public AchievementModelServiceImpl(AchievementModelRepository achievementModelRepository,
                                       UnlockedAchievementModelRepository unlockedAchievementModelRepository,
                                       AchievementModelMapper achievementModelMapper,
                                       PlayerModelRepository playerModelRepository) {
        this.achievementModelRepository = achievementModelRepository;
        this.unlockedAchievementModelRepository = unlockedAchievementModelRepository;
        this.achievementModelMapper = achievementModelMapper;
        this.playerModelRepository = playerModelRepository;
    }

    @Override
    public void project(AchievementCreatedEvent event) {

        AchievementModel achievement = new AchievementModel();
        achievement.setPlatformAchievementId(event.achievementId());
        achievement.setName(event.name());
        achievement.setDescription(event.description());
        achievement.setPictureUrl(event.pictureUrl());
        achievement.setType(AchievementModelType.PLATFORM);
        achievement.setRequiredValue(event.requiredValue());
        achievement.setCreatedAt(event.eventPit());
        achievementModelRepository.save(achievement);

    }

    @Override
    public List<? extends AchievementModelDto> getPlayerAchievements(UUID playerId) {
        List<AchievementModel> allAchievements = achievementModelRepository.findAll();

        List<UnlockedAchievementModel> playerUnlockedAchievements =
                unlockedAchievementModelRepository.findByPlayerId(playerId);

        // Build set of unlocked achievement IDs for quick lookup
        Set<UUID> unlockedAchievementIds = playerUnlockedAchievements.stream()
                .map(UnlockedAchievementModel::getAchievementId)
                .collect(Collectors.toSet());

        // Build map of achievement ID -> unlock timestamp for quick lookup
        Map<UUID, UnlockedAchievementModel> unlockedDataMap = playerUnlockedAchievements.stream()
                .collect(Collectors.toMap(
                        UnlockedAchievementModel::getAchievementId,
                        unlock -> unlock
                ));

        // Query 3: Get player statistics for platform achievement progress
        Map<UUID, Long> playerStatistics = getPlayerStatistics(playerId);

        // Map all achievements to DTOs with unlock status
        return allAchievements.stream()
                .map(achievement -> achievementModelMapper.toAchievementPlayerResponseDto(
                        achievement,
                        unlockedAchievementIds,
                        playerStatistics,
                        unlockedDataMap
                ))
                .sorted(Comparator.comparing(PlayerAchievementModelDto::unlocked).reversed())
                .collect(Collectors.toList());
    }

    //TODO figure out how to get the statistics
    private Map<UUID, Long> getPlayerStatistics(UUID playerId) {
        return playerModelRepository.findById(playerId)
                .map(player -> {
                    Map<UUID, Long> stats = new HashMap<>();

                    // Map player statistics to achievement progress
                    // The keys would be achievement IDs for platform achievements
                    // The values would be the current progress

                    // Example logic (you'll need to adjust based on your actual data):
                    // - For PLAYCOUNT achievements: use player.getTotalGamesPlayed()
                    // - For WINCOUNT achievements: use player.getTotalWins()
                    // - For FRIENDCOUNT achievements: use player.getTotalFriends()
                    // - For RECORDTIME achievements: use player.getBestRecordTime()

                    // This will be populated based on your PlayerModel structure

                    return stats;
                })
                .orElse(new HashMap<>());
    }

    @Override
    public List<? extends AchievementModelDto> getAllPlatformAchievements() {
        return achievementModelRepository.findByGameIdIsNull().stream()
                .map(achievementModelMapper::toAchievementAdminResponseDto)
                .collect(Collectors.toList());
    }

}

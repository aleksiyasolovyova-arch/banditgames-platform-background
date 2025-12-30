package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.content.port.in.PlayerBefriendedCommand;
import be.kdg.team11.content.port.in.PlayerBefriendedProjector;
import be.kdg.team11.content.port.out.LoadPlatformAchievementPort;
import be.kdg.team11.content.port.out.LoadPlayerStatisticsPort;
import be.kdg.team11.content.port.out.SavePlayerStatisticsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerBefriendedProjectorImpl implements PlayerBefriendedProjector {
    private final LoadPlayerStatisticsPort loadPlayerStatisticsPort;
    private final List<SavePlayerStatisticsPort> savePlayerStatisticsPorts;
    private final LoadPlatformAchievementPort loadPlatformAchievementPort;

    public PlayerBefriendedProjectorImpl(LoadPlayerStatisticsPort loadPlayerStatisticsPort, List<SavePlayerStatisticsPort> savePlayerStatisticsPorts, LoadPlatformAchievementPort loadPlatformAchievementPort) {
        this.loadPlayerStatisticsPort = loadPlayerStatisticsPort;
        this.savePlayerStatisticsPorts = savePlayerStatisticsPorts;
        this.loadPlatformAchievementPort = loadPlatformAchievementPort;
    }

    @Override
    public void project(PlayerBefriendedCommand command) {
        PlayerStatistics requesterStatistics = loadPlayerStatisticsPort.loadBy(command.requesterId()).orElseThrow(() -> PlayerStatistics.notFound(command.requesterId()));
        PlayerStatistics recipientStatistics = loadPlayerStatisticsPort.loadBy(command.recipientId()).orElseThrow(() -> PlayerStatistics.notFound(command.recipientId()));
        recipientStatistics.addFriend();
        requesterStatistics.addFriend();

        List<PlatformAchievement> possiblePlatformAchievementsRequester = loadPlatformAchievementPort.loadAllExcept(requesterStatistics.getUnlockedPlatformAchievements());
        List<PlatformAchievement> possiblePlatformAchievementsRecipient = loadPlatformAchievementPort.loadAllExcept(recipientStatistics.getUnlockedPlatformAchievements());
        possiblePlatformAchievementsRequester.forEach(
                platformAchievement -> {
                    if (platformAchievement.isMet(requesterStatistics)) {
                        requesterStatistics.unlockPlatformAchievement(platformAchievement.getAchievementId());
                    }
                }
        );
        possiblePlatformAchievementsRecipient.forEach(
                platformAchievement -> {
                    if (platformAchievement.isMet(recipientStatistics)) {
                        recipientStatistics.unlockPlatformAchievement(platformAchievement.getAchievementId());
                    }
                }
        );

        savePlayerStatisticsPorts.forEach(savePlayerStatisticsPort -> savePlayerStatisticsPort.save(requesterStatistics));
        savePlayerStatisticsPorts.forEach(savePlayerStatisticsPort -> savePlayerStatisticsPort.save(recipientStatistics));
    }
}

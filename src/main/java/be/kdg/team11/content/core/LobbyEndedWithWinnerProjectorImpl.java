package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.content.port.in.LobbyEndedWithWinnerCommand;
import be.kdg.team11.content.port.in.LobbyEndedWithWinnerProjector;
import be.kdg.team11.content.port.out.LoadPlatformAchievementPort;
import be.kdg.team11.content.port.out.LoadPlayerStatisticsPort;
import be.kdg.team11.content.port.out.SavePlayerStatisticsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LobbyEndedWithWinnerProjectorImpl implements LobbyEndedWithWinnerProjector {
    private final LoadPlayerStatisticsPort loadPlayerStatisticsPort;
    private final List<SavePlayerStatisticsPort> savePlayerStatisticsPorts;
    private final LoadPlatformAchievementPort loadPlatformAchievementPort;

    public LobbyEndedWithWinnerProjectorImpl(LoadPlayerStatisticsPort loadPlayerStatisticsPort, List<SavePlayerStatisticsPort> savePlayerStatisticsPorts, LoadPlatformAchievementPort loadPlatformAchievementPort) {
        this.loadPlayerStatisticsPort = loadPlayerStatisticsPort;
        this.savePlayerStatisticsPorts = savePlayerStatisticsPorts;
        this.loadPlatformAchievementPort = loadPlatformAchievementPort;
    }

    @Override
    public void project(LobbyEndedWithWinnerCommand command) {
        PlayerStatistics player1Statistics = loadPlayerStatisticsPort.loadBy(command.player1Id()).orElseThrow(() -> PlayerStatistics.notFound(command.player1Id()));
        PlayerStatistics player2Statistics = loadPlayerStatisticsPort.loadBy(command.player2Id()).orElseThrow(() -> PlayerStatistics.notFound(command.player2Id()));

        if (command.winnerId() == command.player1Id()){
            player1Statistics.addWin();
            player2Statistics.addGamePlayed();
        } else {
            player1Statistics.addGamePlayed();
            player2Statistics.addWin();
        }

        player1Statistics.setBestRecordTime(command.time());
        player2Statistics.setBestRecordTime(command.time());

        List<PlatformAchievement> possiblePlatformAchievementsPlayer1 = loadPlatformAchievementPort.loadAllExcept(player1Statistics.getUnlockedPlatformAchievements());
        List<PlatformAchievement> possiblePlatformAchievementsPlayer2 = loadPlatformAchievementPort.loadAllExcept(player2Statistics.getUnlockedPlatformAchievements());
        possiblePlatformAchievementsPlayer1.forEach(
                platformAchievement -> {
                    if (platformAchievement.isMet(player1Statistics)){
                        player2Statistics.unlockPlatformAchievement(platformAchievement.getAchievementId());
                    }
                }
        );
        possiblePlatformAchievementsPlayer2.forEach(
                platformAchievement -> {
                    if (platformAchievement.isMet(player2Statistics)){
                        player2Statistics.unlockPlatformAchievement(platformAchievement.getAchievementId());
                    }
                }
        );
        savePlayerStatisticsPorts.forEach(
                port ->{
                    port.save(player1Statistics);
                }
        );
        savePlayerStatisticsPorts.forEach(
                port -> {
                    port.save(player2Statistics);
                }
        );
    }
}

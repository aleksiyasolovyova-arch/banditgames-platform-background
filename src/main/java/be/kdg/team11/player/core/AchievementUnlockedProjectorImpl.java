package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.AchievementId;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.AchievementUnlockedCommand;
import be.kdg.team11.player.port.in.AchievementUnlockedProjector;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AchievementUnlockedProjectorImpl implements AchievementUnlockedProjector {
    private final LoadPlayerPort loadPlayerPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public AchievementUnlockedProjectorImpl(LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
    }


    @Override
    public void project(AchievementUnlockedCommand command) {
        Player player = loadPlayerPort.loadBy(PlayerId.of(command.playerId())).orElseThrow(() -> PlayerId.notFound(command.playerId()));
        player.unlockAchievement(AchievementId.of(command.achievementId()));
        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));
    }
}

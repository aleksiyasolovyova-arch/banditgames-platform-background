package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.PlatformAchievementId;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.PlatformAchievementUnlockedCommand;
import be.kdg.team11.player.port.in.PlatformAchievementUnlockedProjector;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlatformAchievementUnlockedProjectorImpl implements PlatformAchievementUnlockedProjector {
    private final LoadPlayerPort loadPlayerPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public PlatformAchievementUnlockedProjectorImpl(LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
    }


    @Override
    public void project(PlatformAchievementUnlockedCommand command) {
        Player player = loadPlayerPort.loadBy(PlayerId.of(command.playerId())).orElseThrow(() -> PlayerId.notFound(command.playerId()));
        player.unlockPlatformAchievement(PlatformAchievementId.of(command.achievementId()));
        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));
    }
}

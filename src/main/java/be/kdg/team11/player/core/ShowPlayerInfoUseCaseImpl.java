package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.ShowPlayerInfoCommand;
import be.kdg.team11.player.port.in.ShowPlayerInfoPort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShowPlayerInfoUseCaseImpl implements ShowPlayerInfoPort {
    private final LoadPlayerPort loadPlayerPort;
    public ShowPlayerInfoUseCaseImpl(LoadPlayerPort loadPlayerPort){
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public Player showInfo(ShowPlayerInfoCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        return loadPlayerPort.loadBy(playerId).orElseThrow(() -> PlayerId.notFound(playerId.playerId()));
    }
}

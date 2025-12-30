package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.RemoveFavoriteGameCommand;
import be.kdg.team11.player.port.in.RemoveFavoriteGamePort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class RemoveFavoriteGameUseCaseImpl implements RemoveFavoriteGamePort {
    private final LoadPlayerPort loadPlayerPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public RemoveFavoriteGameUseCaseImpl(LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
    }

    @Override
    public Player unfavoriteGame(RemoveFavoriteGameCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        Player player = loadPlayerPort.loadBy(playerId)
                .orElseThrow(() -> PlayerId.notFound(command.playerId()));
        player.removeFavoriteGame();

        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}

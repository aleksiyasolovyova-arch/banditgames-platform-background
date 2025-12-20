package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.UnfavoriteGameCommand;
import be.kdg.team11.player.port.in.UnfavoriteGamePort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class UnfavoriteGameUseCaseImpl implements UnfavoriteGamePort {
    private final LoadPlayerPort loadPlayerPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public UnfavoriteGameUseCaseImpl(LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
    }

    @Override
    public Player unfavoriteGame(UnfavoriteGameCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        GameReference gameReference = GameReference.of(command.gameId());

        Player player = loadPlayerPort.loadBy(playerId)
                .orElseThrow(() -> PlayerId.notFound(command.playerId()));
        player.unfavoriteGame(gameReference);

        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}

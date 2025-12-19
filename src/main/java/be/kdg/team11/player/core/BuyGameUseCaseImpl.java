package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.BuyGameCommand;
import be.kdg.team11.player.port.in.BuyGamePort;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BuyGameUseCaseImpl implements BuyGamePort {
    private final LoadPlayerPort loadPlayerPort;
    private final GameReferenceExistsPort gameReferenceExistsPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public BuyGameUseCaseImpl(LoadPlayerPort loadPlayerPort,
                              GameReferenceExistsPort gameReferenceExistsPort,
                              List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.gameReferenceExistsPort = gameReferenceExistsPort;
        this.savePlayerPorts = savePlayerPorts;
    }

    @Override
    public Player buyGame(BuyGameCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        GameReference gameReference = GameReference.of(command.gameId());


        if (!gameReferenceExistsPort.exists(gameReference)) {
            throw GameReference.notFound(gameReference.gameId());
        }

        Player player = loadPlayerPort.loadBy(playerId)
                .orElseThrow(() -> PlayerId.notFound(command.playerId()));

        player.buyGame(gameReference);

        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}

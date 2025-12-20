package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.CreatePlayerCommand;
import be.kdg.team11.player.port.in.CreatePlayerPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreatePlayerUseCaseImpl implements CreatePlayerPort {
    private final List<SavePlayerPort> savePlayerPorts;
    private final LoadGameReferencePort loadGameReferencePort;

    public CreatePlayerUseCaseImpl(List<SavePlayerPort> savePlayerPorts,
                                   LoadGameReferencePort loadGameReferencePort) {
        this.savePlayerPorts = savePlayerPorts;
        this.loadGameReferencePort = loadGameReferencePort;
    }

    @Override
    public Player create(CreatePlayerCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());

        // Create the player (initially without games)
        Player player = Player.create(
                playerId,
                command.username()
        );

        // TODO: TESTING ONLY - Remove this in production
        // Add all existing games as owned games for testing purposes
        List<GameReference> gameReferences = loadGameReferencePort.loadAll();
        for (GameReference gameReference : gameReferences) {
            player.buyGame(gameReference);
        }

        // Save player through all ports
        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}

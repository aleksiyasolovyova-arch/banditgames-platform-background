package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.CreateNewPlayerCommand;
import be.kdg.team11.player.port.in.CreateNewPlayerPort;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateNewPlayerUseCaseImpl implements CreateNewPlayerPort {
    private final SavePlayerPort savePlayerPort;
    private final GameReferenceExistsPort gameReferenceExistsPort;

    public CreateNewPlayerUseCaseImpl(SavePlayerPort savePlayerPort,
                                      GameReferenceExistsPort gameReferenceExistsPort) {
        this.savePlayerPort = savePlayerPort;
        this.gameReferenceExistsPort = gameReferenceExistsPort;
    }

    @Override
    public Player createNewPlayer(CreateNewPlayerCommand command) {
        Player newPlayer = new Player(command.playerId());

        // Load all games and assign to player
        // remove if paying system implemented
        List<GameProjection> allGames = gameReferenceExistsPort.loadAll());
        for (GameProjection gameProjection : allGames) {
            newPlayer.addOwnedGame(gameProjection.gameId());
        }

        return savePlayerPort.save(newPlayer);
    }
}

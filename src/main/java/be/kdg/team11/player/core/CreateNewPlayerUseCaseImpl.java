package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.CreateNewPlayerCommand;
import be.kdg.team11.player.port.in.CreateNewPlayerPort;
import be.kdg.team11.player.port.out.LoadGameProjectionsPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateNewPlayerUseCaseImpl implements CreateNewPlayerPort {
    private final SavePlayerPort savePlayerPort;
    private final LoadGameProjectionsPort loadGameProjectionsPort;

    public CreateNewPlayerUseCaseImpl(SavePlayerPort savePlayerPort,
                                      LoadGameProjectionsPort loadGameProjectionsPort) {
        this.savePlayerPort = savePlayerPort;
        this.loadGameProjectionsPort = loadGameProjectionsPort;
    }

    @Override
    public Player createNewPlayer(CreateNewPlayerCommand command) {
        Player newPlayer = new Player(command.playerId());

        // Load all games and assign to player
        // remove if paying system implemented
        List<GameProjection> allGames = loadGameProjectionsPort.loadAll());
        for (GameProjection gameProjection : allGames) {
            newPlayer.addOwnedGame(gameProjection.gameId());
        }

        return savePlayerPort.save(newPlayer);
    }
}

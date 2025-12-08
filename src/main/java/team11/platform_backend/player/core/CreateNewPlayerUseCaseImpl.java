package team11.platform_backend.player.core;

import org.springframework.stereotype.Service;
import team11.platform_backend.player.domain.player.Player;
import team11.platform_backend.player.domain.player.PlayerId;
import team11.platform_backend.player.domain.projections.GameProjection;
import team11.platform_backend.player.port.in.CreateNewPlayerCommand;
import team11.platform_backend.player.port.in.CreateNewPlayerPort;
import team11.platform_backend.player.port.out.LoadGameProjectionsPort;
import team11.platform_backend.player.port.out.SavePlayerPort;

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
        List<GameProjection> allGames = loadGameProjectionsPort.loadAllGameProjections());
        for (GameProjection gameProjection : allGames) {
            newPlayer.addOwnedGame(gameProjection.getGameId());
        }

        return savePlayerPort.save(newPlayer);
    }
}

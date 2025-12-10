package team11.platform_backend.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.port.in.AcceptGameCommand;
import team11.platform_backend.game.port.in.AcceptGamePort;
import team11.platform_backend.game.port.out.LoadGamePort;
import team11.platform_backend.game.port.out.SaveGamePort;

import java.util.List;

@Service
@Transactional
public class AcceptGameUseCaseImpl implements AcceptGamePort {
    private final List<LoadGamePort> loadGamePorts;
    private final List<SaveGamePort> saveGamePorts;

    public AcceptGameUseCaseImpl(List<LoadGamePort> loadGamePorts,
                                 List<SaveGamePort> saveGamePorts) {
        this.loadGamePorts = loadGamePorts;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game acceptGame(AcceptGameCommand command) {
        // 1. Load the game aggregate
        GameId gameId = new GameId(command.gameId());
        Game game = loadGamePorts.stream()
                .map(port -> port.loadBy(gameId))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Game not found with ID: " + command.gameId()));

        // 2. Execute domain logic (state transition + validation)
        game.acceptGame();

        // 3. Persist the updated aggregate
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

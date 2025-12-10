package be.kdg.team11.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.game.domain.game.Game;
import be.kdg.team11.game.domain.game.GameId;
import be.kdg.team11.game.port.in.RejectGameCommand;
import be.kdg.team11.game.port.in.RejectGamePort;
import be.kdg.team11.game.port.out.LoadGamePort;
import be.kdg.team11.game.port.out.SaveGamePort;

import java.util.List;

@Service
@Transactional
public class RejectGameUseCaseImpl implements RejectGamePort{

    private final List<LoadGamePort> loadGamePorts;
    private final List<SaveGamePort> saveGamePorts;

    public RejectGameUseCaseImpl(List<LoadGamePort> loadGamePorts,
                                 List<SaveGamePort> saveGamePorts) {
        this.loadGamePorts = loadGamePorts;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game rejectGame(RejectGameCommand command) {
        // 1. Load the game aggregate
        GameId gameId = new GameId(command.gameId());
        Game game = loadGamePorts.stream()
                .map(port -> port.findById(gameId))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Game not found with ID: " + command.gameId()));

        // 2. Execute domain logic (state transition + validation)
        game.rejectGame();

        // 3. Persist the updated aggregate
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

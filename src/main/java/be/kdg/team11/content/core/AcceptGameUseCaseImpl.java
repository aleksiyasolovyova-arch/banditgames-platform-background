package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.AcceptGameCommand;
import be.kdg.team11.content.port.in.AcceptGamePort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        GameId gameId = new GameId(command.gameId());
        Game game = loadGamePorts.stream()
                .map(port -> port.loadBy(gameId))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Game not found with ID: " + command.gameId()));

        game.accept();

        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

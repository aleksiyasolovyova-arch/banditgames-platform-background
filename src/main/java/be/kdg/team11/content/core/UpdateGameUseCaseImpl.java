package be.kdg.team11.content.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.UpdateGameCommand;
import be.kdg.team11.content.port.in.UpdateGamePort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.util.List;

@Service
@Transactional
public class UpdateGameUseCaseImpl implements UpdateGamePort{
    private final List<LoadGamePort> loadGamePorts;
    private final List<SaveGamePort> saveGamePorts;

    public UpdateGameUseCaseImpl(List<LoadGamePort> loadGamePorts,
                                 List<SaveGamePort> saveGamePorts) {
        this.loadGamePorts = loadGamePorts;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game updateGame(UpdateGameCommand command) {
        // 1. Load the existing game aggregate
        GameId gameId = new GameId(command.gameId());
        Game existingGame = loadGamePorts.stream()
                .map(port -> port.findById(gameId))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Game not found with ID: " + command.gameId()));

        // 2. Convert pictureUrls to Url value objects
        List<Url> pictureUrls = command.pictureUrls().stream()
                .map(Url::new)
                .toList();

        // 3. Convert gameUrl to Url value object
        Url gameUrl = new Url(command.gameUrl());

        // 5. Create new Game instance with updated values using the loading constructor
        //    Keep unchanged: gameId, gameCreatorName, gameState, rules
        //    Update: name, description, price, pictureUrls, gameUrl, aiPlayerUrl
        Game updatedGame = new Game(
                existingGame.getGameId(),
                command.gameName(),
                command.gameDescription(),
                command.gamePrice(),
                pictureUrls,
                existingGame.getGameCreatorName(),
                gameUrl,
                existingGame.getGameState(),
                existingGame.getRules()
        );

        // 6. Persist the new aggregate
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(updatedGame));

        return updatedGame;
    }
}

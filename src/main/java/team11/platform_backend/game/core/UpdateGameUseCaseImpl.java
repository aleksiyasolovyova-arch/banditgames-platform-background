package team11.platform_backend.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.port.in.UpdateGameCommand;
import team11.platform_backend.game.port.in.UpdateGamePort;
import team11.platform_backend.game.port.out.LoadGamePort;
import team11.platform_backend.game.port.out.SaveGamePort;
import team11.platform_backend.game.domain.Url;

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
                .map(port -> port.loadBy(gameId))
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
        //    Update: gameName, gameDescription, gamePrice, pictureUrls, gameUrl, aiPlayerUrl
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

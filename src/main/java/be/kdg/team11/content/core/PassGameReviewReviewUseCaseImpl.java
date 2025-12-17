package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.PassGameReviewCommand;
import be.kdg.team11.content.port.in.PassGameReviewPort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PassGameReviewReviewUseCaseImpl implements PassGameReviewPort {
    private final List<LoadGamePort> loadGamePorts;
    private final List<SaveGamePort> saveGamePorts;

    public PassGameReviewReviewUseCaseImpl(List<LoadGamePort> loadGamePorts,
                                           List<SaveGamePort> saveGamePorts) {
        this.loadGamePorts = loadGamePorts;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game passGameReview(PassGameReviewCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePorts.stream()
                .flatMap(port -> port.loadBy(gameId).stream())
                .findFirst()
                .orElseThrow(() -> GameId.notFound(gameId));
        game.pass();

        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

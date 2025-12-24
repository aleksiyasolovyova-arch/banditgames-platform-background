package be.kdg.team11.content.core;

import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.FailGameReviewCommand;
import be.kdg.team11.content.port.in.FailGameReviewPort;
import be.kdg.team11.content.port.out.LoadGamePort;

import java.util.List;

@Service
@Transactional
public class FailGameReviewUseCaseImpl implements FailGameReviewPort {

    private final LoadGamePort loadGamePort;
    private final List<SaveGamePort> saveGamePorts;

    public FailGameReviewUseCaseImpl(LoadGamePort loadGamePort,
                                     List<SaveGamePort> saveGamePort) {
        this.loadGamePort = loadGamePort;
        this.saveGamePorts = saveGamePort;
    }

    @Override
    public Game failGameReview(FailGameReviewCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId)
                .orElseThrow(() -> GameId.notFound(gameId));

        game.fail();
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

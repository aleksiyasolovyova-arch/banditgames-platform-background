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
public class PassGameReviewUseCaseImpl implements PassGameReviewPort {
    private final LoadGamePort loadGamePort;
    private final List<SaveGamePort> saveGamePorts;

    public PassGameReviewUseCaseImpl(LoadGamePort loadGamePort,
                                     List<SaveGamePort> saveGamePorts) {
        this.loadGamePort = loadGamePort;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game passGameReview(PassGameReviewCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId).orElseThrow(() -> GameId.notFound(gameId));

        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

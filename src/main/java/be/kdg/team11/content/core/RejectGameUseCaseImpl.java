package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.RejectGameCommand;
import be.kdg.team11.content.port.in.RejectGamePort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RejectGameUseCaseImpl implements RejectGamePort {

    private final LoadGamePort loadGamePort;
    private final List<SaveGamePort> saveGamePorts;

    public RejectGameUseCaseImpl(LoadGamePort loadGamePort,
                                 List<SaveGamePort> saveGamePort) {
        this.loadGamePort = loadGamePort;
        this.saveGamePorts = saveGamePort;
    }

    @Override
    public Game rejectGame(RejectGameCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId)
                .orElseThrow(() -> GameId.notFound(gameId));

        game.reject();
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.TogglePlayableWithAICommand;
import be.kdg.team11.content.port.in.TogglePlayableWithAIPort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TogglePlayableWithAIUseCaseImpl implements TogglePlayableWithAIPort {
    private final LoadGamePort loadGamePort;
    private final List<SaveGamePort> saveGamePorts;

    public TogglePlayableWithAIUseCaseImpl(
            List<SaveGamePort> saveGamePorts,
            LoadGamePort loadGamePort) {
        this.saveGamePorts = saveGamePorts;
        this.loadGamePort = loadGamePort;
    }

    @Override
    public Game toggle(TogglePlayableWithAICommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId)
                .orElseThrow(() -> GameId.notFound(gameId));
        game.togglePlayableWithAI();
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));
        return game;
    }
}

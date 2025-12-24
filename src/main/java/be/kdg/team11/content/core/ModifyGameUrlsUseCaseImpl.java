package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
import be.kdg.team11.content.port.in.ModifyGameUrlsPort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ModifyGameUrlsUseCaseImpl implements ModifyGameUrlsPort {
    private final LoadGamePort loadGamePort;
    private final List<SaveGamePort> saveGamePorts;

    public ModifyGameUrlsUseCaseImpl(LoadGamePort loadGamePort,
                                     List<SaveGamePort> saveGamePorts) {
        this.loadGamePort = loadGamePort;
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game modifyGameUrls(ModifyGameUrlsCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId)
                .orElseThrow(() -> GameId.notFound(gameId));

        game.modifyUrls(command.pictureUrl(), command.gameUrl());
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

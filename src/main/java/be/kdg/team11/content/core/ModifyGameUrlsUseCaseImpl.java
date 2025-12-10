package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.Url;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
import be.kdg.team11.content.port.in.ModifyGameUrlsPort;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;

import java.util.List;
import java.util.Optional;

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
        // 1. Load the existing game aggregate
        GameId gameId = new GameId(command.gameId());
        Optional<Game> optionalGame = loadGamePort.loadBy(gameId);
        if (optionalGame.isEmpty()) {
            throw new IllegalArgumentException("Game with id " + gameId + " not found");
        }
        Game game = optionalGame.get();

        Url pictureUrl = new Url(command.pictureUrl());
        Url gameUrl = new Url(command.gameUrl());

        game.modifyUrls(pictureUrl, gameUrl);
        // 6. Persist the new aggregate
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }
}

package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.ChangeFavoriteGameCommand;
import be.kdg.team11.player.port.in.ChangeFavouriteGamePort;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChangeFavoriteGameUseCaseImpl implements ChangeFavouriteGamePort {
    private final LoadPlayerPort loadPlayerPort;
    private final LoadGameReferencePort loadGameReferencePort;
    private final List<SavePlayerPort> savePlayerPorts;

    public ChangeFavoriteGameUseCaseImpl(LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts, LoadGameReferencePort loadGameReferencePort) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
        this.loadGameReferencePort = loadGameReferencePort;
    }

    @Override
    public Player favoriteGame(ChangeFavoriteGameCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        GameReference game = loadGameReferencePort.loadBy(command.gameId()).orElseThrow(() -> GameReference.notFound(command.gameId()));
        GameReference favoriteGame = GameReference.of(game.gameId(), game.gameUrl());

        Player player = loadPlayerPort.loadBy(playerId)
                .orElseThrow(() -> PlayerId.notFound(command.playerId()));

        player.changeFavoriteGame(favoriteGame);
        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}

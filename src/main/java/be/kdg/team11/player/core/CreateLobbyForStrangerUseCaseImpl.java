package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.Username;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.CreateLobbyForStrangerCommand;
import be.kdg.team11.player.port.in.CreateLobbyForStrangerPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateLobbyForStrangerUseCaseImpl implements CreateLobbyForStrangerPort {
    private final LoadPlayerPort loadPlayerPort;
    private final LoadGameReferencePort loadGameReferencePort;
    private final List<SaveLobbyPort> saveGameLobbyPorts;

    public CreateLobbyForStrangerUseCaseImpl(LoadPlayerPort loadPlayerPort, LoadGameReferencePort loadGameReferencePort, List<SaveLobbyPort> saveGameLobbyPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.loadGameReferencePort = loadGameReferencePort;
        this.saveGameLobbyPorts = saveGameLobbyPorts;
    }

    @Override
    public Lobby create(CreateLobbyForStrangerCommand command) {
        Player stranger = loadPlayerPort.loadBy(command.strangerUserName()).orElseThrow(() -> Username.notFound(command.strangerUserName()));
        GameReference gameReference = loadGameReferencePort.loadBy(command.gameId()).orElseThrow(() -> GameReference.notFound(command.gameId()));
        Lobby lobby = Lobby.createForStrangers(
                gameReference,
                Pair.of(command.playerId(), stranger.getPlayerId())
        );
        saveGameLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
        return lobby;
    }
}

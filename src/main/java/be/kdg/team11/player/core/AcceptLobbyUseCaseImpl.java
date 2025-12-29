package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.AcceptLobbyCommand;
import be.kdg.team11.player.port.in.AcceptLobbyPort;
import be.kdg.team11.player.port.out.LoadLobbyPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AcceptLobbyUseCaseImpl implements AcceptLobbyPort {
    private final LoadLobbyPort loadLobbyPort;
    private final List<SaveLobbyPort> saveLobbyPorts;

    public AcceptLobbyUseCaseImpl(LoadLobbyPort loadLobbyPort, List<SaveLobbyPort> saveLobbyPorts) {
        this.loadLobbyPort = loadLobbyPort;
        this.saveLobbyPorts = saveLobbyPorts;
    }

    @Override
    public Lobby accept(AcceptLobbyCommand command) {
        LobbyId lobbyId = LobbyId.of(command.lobbyId());
        PlayerId playerId = PlayerId.of(command.playerId());

        Lobby lobby = loadLobbyPort.loadBy(lobbyId)
                .orElseThrow(() -> LobbyId.notFound(command.lobbyId()));

        lobby.acceptBy(playerId);
        saveLobbyPorts.forEach(port -> port.save(lobby));
        return lobby;
    }
}

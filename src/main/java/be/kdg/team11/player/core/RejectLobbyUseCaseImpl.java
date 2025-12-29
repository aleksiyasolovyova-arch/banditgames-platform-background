package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.RejectLobbyCommand;
import be.kdg.team11.player.port.in.RejectLobbyPort;
import be.kdg.team11.player.port.out.LoadLobbyPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RejectLobbyUseCaseImpl implements RejectLobbyPort {
    private final LoadLobbyPort loadLobbyPort;
    private final List<SaveLobbyPort> saveLobbyPorts;

    public RejectLobbyUseCaseImpl(
            LoadLobbyPort loadLobbyPort,
            List<SaveLobbyPort> saveLobbyPorts) {
        this.loadLobbyPort = loadLobbyPort;
        this.saveLobbyPorts = saveLobbyPorts;
    }

    @Override
    public Lobby reject(RejectLobbyCommand command) {
        LobbyId lobbyId = LobbyId.of(command.lobbyId());
        PlayerId playerId = PlayerId.of(command.playerId());

        Lobby lobby = loadLobbyPort.loadBy(lobbyId)
                .orElseThrow(() -> LobbyId.notFound(command.lobbyId()));


        lobby.rejectBy(playerId);

        saveLobbyPorts.forEach(port -> port.save(lobby));

        return lobby;
    }
}

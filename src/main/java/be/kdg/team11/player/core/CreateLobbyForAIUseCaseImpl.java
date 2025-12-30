package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.CreateLobbyForAICommand;
import be.kdg.team11.player.port.in.CreateLobbyForAIPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreateLobbyForAIUseCaseImpl implements CreateLobbyForAIPort {
    private final LoadGameReferencePort loadGameReferencePort;
    private final List<SaveLobbyPort> saveGameLobbyPorts;

    public CreateLobbyForAIUseCaseImpl(LoadGameReferencePort loadGameReferencePort, List<SaveLobbyPort> saveGameLobbyPorts) {
        this.loadGameReferencePort = loadGameReferencePort;
        this.saveGameLobbyPorts = saveGameLobbyPorts;
    }

    @Override
    public Lobby create(CreateLobbyForAICommand command) {
        GameReference gameReference = loadGameReferencePort.loadBy(command.gameId()).orElseThrow(() -> GameReference.notFound(command.gameId()));
        Lobby lobby = Lobby.createForAI(
                gameReference,
                command.playerId()
        );
        saveGameLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
        return lobby;
    }
}

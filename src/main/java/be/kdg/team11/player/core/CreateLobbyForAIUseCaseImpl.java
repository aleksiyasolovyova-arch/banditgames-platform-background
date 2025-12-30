package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.CreateLobbyForAICommand;
import be.kdg.team11.player.port.in.CreateLobbyForAIPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CreateLobbyForAIUseCaseImpl implements CreateLobbyForAIPort {
    private final LoadGameReferencePort loadGameReferencePort;
    private final List<SaveLobbyPort> saveGameLobbyPorts;
    private final String aiPlayerId;

    public CreateLobbyForAIUseCaseImpl(@Value("${AI_PLAYER_ID}") String aiPlayerId, LoadGameReferencePort loadGameReferencePort, List<SaveLobbyPort> saveGameLobbyPorts) {
        this.loadGameReferencePort = loadGameReferencePort;
        this.saveGameLobbyPorts = saveGameLobbyPorts;
        this.aiPlayerId = aiPlayerId;
    }

    @Override
    public Lobby create(CreateLobbyForAICommand command) {
        GameReference gameReference = loadGameReferencePort.loadBy(command.gameId()).orElseThrow(() -> GameReference.notFound(command.gameId()));
        Lobby lobby = Lobby.createForAI(
                gameReference,
                Pair.of(command.playerId(), PlayerId.of(UUID.fromString(aiPlayerId)))
        );
        saveGameLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
        return lobby;
    }
}

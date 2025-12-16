package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.JoinLobbyWithStrangerCommand;
import be.kdg.team11.player.port.in.JoinLobbyWithStrangerPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class JoinLobbyWithStrangerUseCaseImpl implements JoinLobbyWithStrangerPort {
    // Concurrent hash map locks per game id, so two computes on the same Game id will be done sequentially but two computes on two different game ids will be done in parallel
    private final Map<GameReference, PlayerId> matchmakingQueue = new ConcurrentHashMap<>();
    private final List<SaveLobbyPort> saveGameLobbyPorts;

    public JoinLobbyWithStrangerUseCaseImpl(
            List<SaveLobbyPort> saveGameLobbyPorts
    ) {
        this.saveGameLobbyPorts = saveGameLobbyPorts;
    }

    @Override
    public Optional<Lobby> joinLobbyWithStranger(JoinLobbyWithStrangerCommand command) {
        // AtomicReference to reference the game lobby we may create
        AtomicReference<Lobby> createdLobby = new AtomicReference<>();

        matchmakingQueue.compute(command.gameReference(), (key, waitingPlayer) -> {
            if (waitingPlayer == null) {
                // No one waiting - this player becomes the waiting player
                return command.playerId();
            }

            // Someone is waiting - check if it's the same player
            if (waitingPlayer.equals(command.playerId())) {
                throw new IllegalArgumentException("Player already waiting in this queue");
            }

            // Create lobby with both players
            Lobby lobby = Lobby.createForStrangers(command.gameReference(), Pair.of(waitingPlayer, command.playerId()));
            saveGameLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));

            // Store in atomic reference to return later
            createdLobby.set(lobby);

            // Return null to remove from queue (pair is complete)
            return null;
        });

        return Optional.ofNullable(createdLobby.get());
    }
}

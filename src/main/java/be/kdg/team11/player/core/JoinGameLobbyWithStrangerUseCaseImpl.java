package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.AvailableGame;
import be.kdg.team11.player.port.in.JoinGameLobbyWithStrangerPort;
import be.kdg.team11.player.port.out.SaveGameLobbyPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class JoinGameLobbyWithStrangerUseCaseImpl implements JoinGameLobbyWithStrangerPort {
    // Concurrent hash map locks per game id, so two computes on the same Game id will be done sequentially but two computes on two different game ids will be done in parallel
    private final Map<AvailableGame, PlayerId> matchmakingQueue = new ConcurrentHashMap<>();
    private final SaveGameLobbyPort saveGameLobbyPort;

    public JoinGameLobbyWithStrangerUseCaseImpl(
            SaveGameLobbyPort saveGameLobbyPort
    ) {
        this.saveGameLobbyPort = saveGameLobbyPort;
    }

    @Override
    public Optional<Lobby> joinGameLobbyWithStranger(AvailableGame gameId, PlayerId playerId) {
        // AtomicReference to reference the game lobby we may create
        AtomicReference<Lobby> createdLobby = new AtomicReference<>();

        matchmakingQueue.compute(gameId, (key, waitingPlayer) -> {
            if (waitingPlayer == null) {
                // No one waiting - this player becomes the waiting player
                return playerId;
            }

            // Someone is waiting - check if it's the same player
            if (waitingPlayer.equals(playerId)) {
                throw new IllegalArgumentException("Player already waiting in this queue");
            }

            // Create lobby with both players
            Lobby lobby = Lobby.createForStrangers(gameId, waitingPlayer, playerId);
            saveGameLobbyPort.save(lobby);

            // Store in atomic reference to return later
            createdLobby.set(lobby);

            // Return null to remove from queue (pair is complete)
            return null;
        });

        return Optional.ofNullable(createdLobby.get());
    }
}

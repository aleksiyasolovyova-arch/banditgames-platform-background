package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.GameEventPort;
import be.kdg.team11.player.port.out.LoadLobbyPort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import be.kdg.team11.sharedkernel.events.rabbitmq.AchievementUnlockedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameCreatedEvent;
import be.kdg.team11.sharedkernel.events.rabbitmq.GameFinishedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameEventPortImpl implements GameEventPort {
    private final LoadLobbyPort loadLobbyPort;
    private final LoadPlayerPort loadPlayerPort;
    private final List<SaveLobbyPort> saveLobbyPorts;

    public GameEventPortImpl(LoadLobbyPort loadLobbyPort, LoadPlayerPort loadPlayerPort, List<SaveLobbyPort> saveLobbyPorts) {
        this.loadLobbyPort = loadLobbyPort;
        this.loadPlayerPort = loadPlayerPort;
        this.saveLobbyPorts = saveLobbyPorts;

    }

    @Override
    public void onGameCreatedEvent(GameCreatedEvent event) {
        Lobby lobby = loadLobbyPort.loadBy(LobbyId.of(UUID.fromString(event.gameId()))).orElseThrow(() -> LobbyId.notFound(UUID.fromString(event.gameId())));
        lobby.start();
        saveLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
    }

    @Override
    public void onAchievementUnlocked(AchievementUnlockedEvent event) {
        Lobby lobby = loadLobbyPort.loadBy(LobbyId.of(UUID.fromString(event.gameId()))).orElseThrow(() -> LobbyId.notFound(UUID.fromString(event.gameId())));
        GameReference gameReference = lobby.getGameReference();
        Player player = loadPlayerPort.loadBy(PlayerId.of(UUID.fromString(event.playerId()))).orElseThrow(() -> PlayerId.notFound(UUID.fromString(event.playerId())));
        player.unlockGameAchievement(
            gameReference, event.title()
        );
    }

    @Override
    public void onGameFinishedEvent(GameFinishedEvent event) {
        Lobby lobby = loadLobbyPort.loadBy(LobbyId.of(UUID.fromString(event.gameId()))).orElseThrow(() -> LobbyId.notFound(UUID.fromString(event.gameId())));
        if (event.winner() != null) {
            PlayerId winnerId = PlayerId.of(UUID.fromString(event.winner().id()));
            lobby.end(winnerId);
        } else {
            lobby.end();
        }

        saveLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
    }
}

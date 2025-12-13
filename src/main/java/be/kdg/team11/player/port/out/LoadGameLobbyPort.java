package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;

import java.util.Optional;

public interface LoadGameLobbyPort {
    Optional<Lobby> loadBy(LobbyId lobbyId);
}

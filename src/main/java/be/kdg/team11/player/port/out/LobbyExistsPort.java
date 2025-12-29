package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.lobby.LobbyId;

public interface LobbyExistsPort {
    boolean exists(LobbyId lobbyId);
}

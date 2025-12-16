package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.lobby.Lobby;

public interface SaveLobbyPort {
    Lobby save(Lobby lobby);
}

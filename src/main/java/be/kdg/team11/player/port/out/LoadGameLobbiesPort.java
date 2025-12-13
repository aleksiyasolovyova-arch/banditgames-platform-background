package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.lobby.Lobby;

import java.util.List;

public interface LoadGameLobbiesPort {
    List<Lobby> loadAll();
}

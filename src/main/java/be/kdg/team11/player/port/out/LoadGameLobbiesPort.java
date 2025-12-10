package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.gamelobby.GameLobby;

import java.util.List;

public interface LoadGameLobbiesPort {
    List<GameLobby> loadAll();
}

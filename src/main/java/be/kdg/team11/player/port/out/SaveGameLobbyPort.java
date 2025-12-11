package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.gamelobby.GameLobby;

public interface SaveGameLobbyPort {
    GameLobby save(GameLobby gameLobby);
}

package team11.platform_backend.player.port.out;

import team11.platform_backend.player.domain.gamelobby.GameLobby;

public interface SaveGameLobbyPort {
    GameLobby save(GameLobby gameLobby);
}

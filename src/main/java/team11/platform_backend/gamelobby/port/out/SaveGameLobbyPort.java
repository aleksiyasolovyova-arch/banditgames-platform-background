package team11.platform_backend.gamelobby.port.out;

import team11.platform_backend.gamelobby.domain.GameLobby;

public interface SaveGameLobbyPort {
    GameLobby save (GameLobby gameLobby);
}

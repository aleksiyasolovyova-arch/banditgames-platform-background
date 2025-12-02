package team11.platform_backend.gamelobby.port.out;

import team11.platform_backend.gamelobby.domain.GameLobby;

import java.util.List;

public interface LoadGameLobbiesPort {
    List<GameLobby> loadAllGameLobbies();
}

package team11.platform_backend.player.port.out;

import team11.platform_backend.player.domain.gamelobby.GameLobby;

import java.util.List;

public interface LoadGameLobbiesPort {
    List<GameLobby> loadAllGameLobbies();
}

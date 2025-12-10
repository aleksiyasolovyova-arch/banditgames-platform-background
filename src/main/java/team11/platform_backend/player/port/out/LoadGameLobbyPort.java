package team11.platform_backend.player.port.out;

import team11.platform_backend.player.domain.gamelobby.GameLobby;
import team11.platform_backend.player.domain.gamelobby.GameLobbyId;

import java.util.Optional;

public interface LoadGameLobbyPort {
    Optional<GameLobby> loadBy(GameLobbyId gameLobbyId);
}

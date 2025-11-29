package team11.platform_backend.gamelobby.port.out;

import team11.platform_backend.gamelobby.domain.GameLobby;
import team11.platform_backend.gamelobby.domain.GameLobbyId;

import java.util.Optional;

public interface LoadGameLobbyPort {
    Optional<GameLobby> loadById(GameLobbyId gameLobbyId);
}

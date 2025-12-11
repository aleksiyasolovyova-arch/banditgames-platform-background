package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.gamelobby.GameLobby;
import be.kdg.team11.player.domain.gamelobby.GameLobbyId;

import java.util.Optional;

public interface LoadGameLobbyPort {
    Optional<GameLobby> loadBy(GameLobbyId gameLobbyId);
}

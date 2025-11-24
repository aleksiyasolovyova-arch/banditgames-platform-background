package team11.platform_backend.gamesession.domain;

import java.util.UUID;

public record GameLobbyId(
        UUID gameLobbyId
) {
    public static GameLobbyId createGameLobbyId(UUID gameSessionId) {
        return new GameLobbyId(gameSessionId);
    }
}

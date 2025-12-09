package team11.platform_backend.player.domain.gamelobby;

import java.util.UUID;

public record GameLobbyId(
        UUID gameLobbyId
) {
    public static GameLobbyId create() {
        return new GameLobbyId(UUID.randomUUID());
    }
}

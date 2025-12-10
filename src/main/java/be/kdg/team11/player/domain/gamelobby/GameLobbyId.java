package be.kdg.team11.player.domain.gamelobby;

import java.util.UUID;

public record GameLobbyId(
        UUID gameLobbyId
) {
    public static GameLobbyId create() {
        return new GameLobbyId(UUID.randomUUID());
    }
}

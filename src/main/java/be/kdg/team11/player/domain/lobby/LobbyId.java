package be.kdg.team11.player.domain.lobby;

import java.util.UUID;

public record LobbyId(
        UUID lobbyId
) {
    public static LobbyId create() {
        return new LobbyId(UUID.randomUUID());
    }
}

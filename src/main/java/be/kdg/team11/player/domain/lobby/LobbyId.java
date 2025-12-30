package be.kdg.team11.player.domain.lobby;

import be.kdg.team11.player.domain.lobby.exceptions.LobbyNotFoundException;

import java.util.UUID;

public record LobbyId(
        UUID lobbyId
) {
    public static LobbyId create() {
        return new LobbyId(UUID.randomUUID());
    }

    public static LobbyId of(UUID lobbyId) {
        return new LobbyId(lobbyId);
    }

    public static LobbyNotFoundException notFound(UUID lobbyId) {
        return new LobbyNotFoundException(
                String.format("Lobby not found with ID: %s", lobbyId)
        );
    }
}

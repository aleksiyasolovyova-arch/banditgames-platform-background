package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.lobby.exceptions.LobbyNotFoundException;
import be.kdg.team11.player.domain.player.exceptions.InvalidPlayerException;
import be.kdg.team11.player.domain.player.exceptions.PlayerNotFoundException;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    private static final String AI_ID = "00000000-0000-0000-0000-000000a1face";

    public PlayerId {
        if (playerId == null) {
            throw new InvalidPlayerException("Player ID cannot be null");
        }
    }

    public static PlayerId of(UUID playerId) {
        return new PlayerId(playerId);
    }

    public static PlayerId ai() {
        return new PlayerId(UUID.fromString(AI_ID));
    }

    public static PlayerId create() {
        return new PlayerId(UUID.randomUUID());
    }

    public boolean isAI() {
        return playerId.equals(UUID.fromString(AI_ID));
    }


    public static PlayerNotFoundException notFound(UUID playerId) {
        return new PlayerNotFoundException(
                String.format("Player not found with ID: %s", playerId)
        );
    }
}

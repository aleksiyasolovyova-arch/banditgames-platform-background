package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.PlayerNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    public static PlayerId of(UUID playerId) {
        return new PlayerId(playerId);
    }

    public static PlayerId create() {
        return new PlayerId(UUID.randomUUID());
    }

    public static PlayerNotFoundException notFound(UUID playerId) {
        return new PlayerNotFoundException(
                String.format("Player not found with ID: %s", playerId)
        );
    }
}

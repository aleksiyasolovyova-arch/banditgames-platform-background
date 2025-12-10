package be.kdg.team11.game.adapter.in.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AcceptGameRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId
) {
}

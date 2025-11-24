package team11.platform_backend.game.adapter.in.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RejectGameRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId
) {
}

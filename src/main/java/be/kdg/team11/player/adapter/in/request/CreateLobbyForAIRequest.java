package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateLobbyForAIRequest(
        @NotNull
        UUID gameId
) {
}

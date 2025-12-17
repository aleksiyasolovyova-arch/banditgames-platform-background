package be.kdg.team11.content.adapter.in.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PassGameReviewRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId
) {
}

package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateLobbyForStrangerRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId,
        @NotBlank(message = "Stranger username cannot be blank")
        String strangerUserName
) {
}

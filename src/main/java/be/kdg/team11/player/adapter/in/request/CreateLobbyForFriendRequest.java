package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateLobbyForFriendRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId,
        @NotNull(message = "Friend ID cannot be null")
        UUID friendId
) {
}

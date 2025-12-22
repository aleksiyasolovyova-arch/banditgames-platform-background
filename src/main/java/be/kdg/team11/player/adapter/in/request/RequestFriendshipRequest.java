package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotNull;

public record RequestFriendshipRequest(
        @NotNull(message = "Recipient ID cannot be null")
        String recipientUsername
) {}

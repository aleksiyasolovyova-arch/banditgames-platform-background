package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestFriendshipRequest(
        @NotEmpty(message = "Recipient username cannot be empty")
        String recipientUsername
) {}

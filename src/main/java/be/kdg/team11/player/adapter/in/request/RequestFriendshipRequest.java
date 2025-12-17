package be.kdg.team11.player.adapter.in.request;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestFriendshipRequest(
        @NotNull(message = "Recipient ID cannot be null")
        UUID recipientId
) {
    public RequestFriendshipRequest {
        if (recipientId == null) {
            throw new InvalidFriendshipException("Recipient ID cannot be null");
        }
    }
}

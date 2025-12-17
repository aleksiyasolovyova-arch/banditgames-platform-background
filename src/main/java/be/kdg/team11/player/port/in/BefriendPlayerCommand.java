package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;

import java.util.UUID;

public record BefriendPlayerCommand(
        UUID friendshipId,
        UUID recipientId
) {
    public BefriendPlayerCommand {
        if (friendshipId == null) {
            throw new InvalidFriendshipException("Friendship ID cannot be null");
        }
        if (recipientId == null) {
            throw new InvalidFriendshipException("Recipient ID cannot be null");
        }
    }
}

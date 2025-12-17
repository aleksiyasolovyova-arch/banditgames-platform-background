package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;

import java.util.UUID;

public record RequestFriendshipCommand(
        UUID requesterId,
        UUID recipientId
) {
    public RequestFriendshipCommand {
        if (requesterId == null) {
            throw new InvalidFriendshipException("Requester ID cannot be null");
        }
        if (recipientId == null) {
            throw new InvalidFriendshipException("Recipient ID cannot be null");
        }
        if (requesterId.equals(recipientId)) {
            throw new InvalidFriendshipException("A player cannot request friendship with themselves");
        }
    }
}

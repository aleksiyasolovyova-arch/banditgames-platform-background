package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;

import java.util.UUID;

public record EndFriendshipCommand(
        UUID friendshipId,
        UUID initiatedBy
) {
    public EndFriendshipCommand {
        if (friendshipId == null) {
            throw new InvalidFriendshipException("Friendship ID cannot be null");
        }
        if (initiatedBy == null) {
            throw new InvalidFriendshipException("Initiated by player ID cannot be null");
        }
    }
}

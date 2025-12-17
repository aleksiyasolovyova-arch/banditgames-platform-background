package be.kdg.team11.player.domain.friendship;

import be.kdg.team11.player.domain.friendship.exceptions.FriendshipNotFoundException;
import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;

import java.util.UUID;

public record FriendshipId(
        UUID friendshipId
) {
    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }
    public static FriendshipId of (UUID friendshipId) {
        if (friendshipId == null) {
            throw new InvalidFriendshipException("Friendship ID UUID cannot be null");
        }
        return new FriendshipId(friendshipId);
    }

    public static FriendshipNotFoundException notFound(FriendshipId friendshipId) {
        return new FriendshipNotFoundException(
                String.format("Friendship not found with ID: %s", friendshipId.friendshipId())
        );
    }
}

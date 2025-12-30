package be.kdg.team11.player.domain.friendship;

import be.kdg.team11.player.domain.friendship.exceptions.FriendRequestAlreadyExistsException;
import be.kdg.team11.player.domain.friendship.exceptions.FriendshipNotFoundException;

import java.util.UUID;

public record FriendshipId(
        UUID friendshipId
) {
    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }

    public static FriendshipId of(UUID friendshipId) {
        return new FriendshipId(friendshipId);
    }


    public static FriendshipNotFoundException notFound(FriendshipId friendshipId) {
        return new FriendshipNotFoundException(
                String.format("Friendship not found with ID: %s", friendshipId.friendshipId())
        );
    }

    public static FriendRequestAlreadyExistsException alreadyExists() {
        return new FriendRequestAlreadyExistsException("Friend request already exists");
    }
}

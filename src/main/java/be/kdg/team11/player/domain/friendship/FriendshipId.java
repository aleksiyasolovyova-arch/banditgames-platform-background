package be.kdg.team11.player.domain.friendship;

import java.util.UUID;

public record FriendshipId(
        UUID friendshipId
) {
    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }
    public static FriendshipId of (UUID friendshipId) {
        return new FriendshipId(friendshipId);
    }
}

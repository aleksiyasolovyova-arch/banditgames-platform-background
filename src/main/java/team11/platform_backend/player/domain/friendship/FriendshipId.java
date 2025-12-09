package team11.platform_backend.player.domain.friendship;

import java.util.UUID;

public record FriendshipId(
        UUID friendshipId
) {
    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }
}

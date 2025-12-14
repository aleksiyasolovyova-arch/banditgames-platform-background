package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;

import java.util.Optional;

public interface LoadFriendshipPort {
    Optional<Friendship> loadBy(FriendshipId friendshipId);
}
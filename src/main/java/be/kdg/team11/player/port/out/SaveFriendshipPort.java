package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.friendship.Friendship;

public interface SaveFriendshipPort {
    Friendship save(Friendship friendship);
}

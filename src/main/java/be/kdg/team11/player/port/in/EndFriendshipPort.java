package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.Friendship;

public interface EndFriendshipPort {
    Friendship endFriendship(EndFriendshipCommand command);
}

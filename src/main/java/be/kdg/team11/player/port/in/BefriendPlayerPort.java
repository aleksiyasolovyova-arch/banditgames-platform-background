package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.Friendship;

public interface BefriendPlayerPort {
    Friendship befriendPlayer(BefriendPlayerCommand command);
}

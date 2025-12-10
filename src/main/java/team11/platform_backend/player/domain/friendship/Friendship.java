package team11.platform_backend.player.domain.friendship;

import org.springframework.data.util.Pair;
import team11.platform_backend.player.domain.friendship.exeptions.InvalidFriendshipStateException;
import team11.platform_backend.player.domain.player.PlayerId;

//Aggregate
public class Friendship {
    private final FriendshipId friendshipId;
    private final Pair<PlayerId,PlayerId> playerIdPair;
    private FriendshipState friendshipState;

    public Friendship(FriendshipId friendshipId, Pair<PlayerId, PlayerId> playerIdPair, FriendshipState friendshipState) {
        this.friendshipId = friendshipId;
        this.playerIdPair = playerIdPair;
        this.friendshipState = friendshipState;
    }

    public Friendship(Pair<PlayerId, PlayerId> playerIdPair) {
        this.friendshipId = FriendshipId.create();
        this.playerIdPair = playerIdPair;
        this.friendshipState = FriendshipState.PENDING;
    }

    public void acceptFriendship() {
        if (this.friendshipState != FriendshipState.PENDING) {
            throw new InvalidFriendshipStateException(
                    "Cannot accept friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.ACCEPTED;
    }

    public FriendshipId getFriendshipId() {
        return friendshipId;
    }

    public Pair<PlayerId, PlayerId> getPlayerIdPair() {
        return playerIdPair;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }
}

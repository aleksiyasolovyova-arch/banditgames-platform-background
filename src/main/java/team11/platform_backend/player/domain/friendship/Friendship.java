package team11.platform_backend.player.domain.friendship;

import team11.platform_backend.game.domain.game.exeptions.InvalidGameStateException;
import team11.platform_backend.player.domain.friendship.exeptions.InvalidFriendshipStateException;
import team11.platform_backend.player.domain.player.PlayerId;

//Aggregate
public class Friendship {
    private final FriendshipId friendshipId;
    private final PlayerId player1Id;
    private final PlayerId player2Id;
    private FriendshipState friendshipState;

    // for getting (get methods)
    public Friendship(FriendshipId friendshipId, PlayerId player1Id, PlayerId player2Id, FriendshipState friendshipState) {
        this.friendshipId = friendshipId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.friendshipState = friendshipState;
    }

    //for creating (post methods)
    public Friendship(PlayerId player1Id, PlayerId player2Id) {
        this.friendshipId = FriendshipId.createFriendshipId();
        this.player1Id = player1Id;
        this.player2Id = player2Id;
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

    public PlayerId getPlayer1Id() {
        return player1Id;
    }

    public PlayerId getPlayer2Id() {
        return player2Id;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }
}

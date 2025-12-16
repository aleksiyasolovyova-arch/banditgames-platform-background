package be.kdg.team11.player.domain.friendship;

import be.kdg.team11.player.domain.friendship.exeptions.InvalidFriendshipStateException;
import be.kdg.team11.player.domain.player.PlayerId;
import org.springframework.data.util.Pair;

//Aggregate
public class Friendship {
    private final FriendshipId friendshipId;
    private final Pair<PlayerId, PlayerId> playerIdPair;
    private FriendshipState friendshipState;

    public Friendship(FriendshipId friendshipId, Pair<PlayerId, PlayerId> playerIdPair, FriendshipState friendshipState) {
        this.friendshipId = friendshipId;
        this.playerIdPair = playerIdPair;
        this.friendshipState = friendshipState;
    }

    public static Friendship create(Pair<PlayerId,PlayerId> playerIdPair){
        return new Friendship(
                FriendshipId.create(),
                playerIdPair,
                FriendshipState.PENDING
        );
    }

    public void accept() {
        if (this.friendshipState != FriendshipState.PENDING) {
            throw new InvalidFriendshipStateException(
                    "Cannot accept friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.ACCEPTED;
    }

    public void reject() {
        if (this.friendshipState != FriendshipState.PENDING) {
            throw new InvalidFriendshipStateException(
                    "Cannot reject friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.REJECTED;
    }

    public PlayerId getRequester() {
        return playerIdPair.getFirst();
    }

    public PlayerId getRecipient() {
        return playerIdPair.getSecond();
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

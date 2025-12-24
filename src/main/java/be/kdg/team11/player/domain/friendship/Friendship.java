package be.kdg.team11.player.domain.friendship;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;
import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipStateException;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate Root for the Friendship subdomain.
 * Represents a bidirectional friendship relationship between two players.
  */
public class Friendship {
    private final FriendshipId friendshipId;
    private final Pair<PlayerId, PlayerId> playerIdPair;
    private FriendshipState friendshipState;
    private final List<DomainEvent> eventStore = new ArrayList<>();


/**
 * Constructor for recreating a friendship from persistent storage.
 * All parameters are required and must be valid.
 */
    public Friendship(FriendshipId friendshipId, Pair<PlayerId, PlayerId> playerIdPair, FriendshipState friendshipState) {
        validatePlayerIdsDifferent(playerIdPair);

        this.friendshipId = friendshipId;
        this.playerIdPair = playerIdPair;
        this.friendshipState = friendshipState;
    }

/**
 * Static factory method for creating a new friendship with full validation.
 * Generates a new friendship ID with PENDING initial state.
 * Publishes a FriendshipCreatedEvent to the event store.
 */
    public static Friendship create(Pair<PlayerId,PlayerId> playerIdPair){
        validatePlayerIdsDifferent(playerIdPair);

        Friendship friendship = new Friendship(FriendshipId.create(),playerIdPair,FriendshipState.REQUESTED);

        FriendshipCreatedEvent event = new FriendshipCreatedEvent(
                friendship.friendshipId.friendshipId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                FriendshipState.REQUESTED.name()
        );
        friendship.eventStore.add(event);

        return friendship;
    }

/**
 * Transitions the friendship from REQUESTED to FRIENDS state.
 * Can only be called when the friendship is in REQUESTED state.
 * Publishes a BefriendedPlayerEvent to the event store.
 */
    public void befriend() {
        if (this.friendshipState != FriendshipState.REQUESTED) {
            throw new InvalidFriendshipStateException(
                    "Cannot accept friendship: current state is " + this.friendshipState + ", expected REQUESTED"
            );
        }
        this.friendshipState = FriendshipState.FRIENDS;

        BefriendedPlayerEvent event = new BefriendedPlayerEvent(
                this.friendshipId.friendshipId(),
                this.playerIdPair.getSecond().playerId(),
                FriendshipState.FRIENDS.name()
        );

        this.eventStore.add(event);
    }
/**
 * Transitions the friendship from REQUESTED to DECLINED state.
 * Can only be called when the friendship is in REQUESTED state.
 * Publishes a DeclineFriendshipEvent to the event store.
 */
    public void decline() {
        if (this.friendshipState != FriendshipState.REQUESTED) {
            throw new InvalidFriendshipStateException(
                    "Cannot decline friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.DECLINED;
        FriendshipDeclinedEvent event = new FriendshipDeclinedEvent(
                this.friendshipId.friendshipId(),
                this.playerIdPair.getSecond().playerId(),
                FriendshipState.DECLINED.name()
        );

        this.eventStore.add(event);
    }

/**
 * Terminates an active friendship relationship.
 * Can only be called when the friendship is in ACCEPTED state.
 * Publishes a FriendshipEndedEvent to the event store.
 */
    public void end(PlayerId initiatedBy) {
        if (!involvesPlayer(initiatedBy)) {
            throw new InvalidFriendshipException(
                    String.format("Player %s is not involved in this friendship and cannot end it", initiatedBy.playerId())
            );
        }

        if (this.friendshipState != FriendshipState.FRIENDS) {
            throw new InvalidFriendshipStateException(
                    "Cannot end friendship: current state is " + this.friendshipState + ", expected FRIENDS"
            );
        }

        this.friendshipState = FriendshipState.DECLINED;

        FriendshipEndEvent event = new FriendshipEndEvent(
                this.friendshipId.friendshipId(),
                initiatedBy.playerId(),
                FriendshipState.DECLINED.name()
        );

        this.eventStore.add(event);
    }


/**
 * Returns the other player involved in the friendship.
 * Given one player ID, returns the other player in the pair.
 */
    public PlayerId getOtherPlayer(PlayerId playerId) {
        if (playerIdPair.getFirst().equals(playerId)) {
            return playerIdPair.getSecond();
        }

        if (playerIdPair.getSecond().equals(playerId)) {
            return playerIdPair.getFirst();
        }

        throw new InvalidFriendshipException(
                String.format("Player %s is not involved in this friendship", playerId.playerId())
        );
    }

    public boolean involvesPlayer(PlayerId playerId) {
        return playerIdPair.getFirst().equals(playerId) || playerIdPair.getSecond().equals(playerId);
    }



    private static void validatePlayerIdsDifferent(Pair<PlayerId, PlayerId> playerIdPair) {
        if (playerIdPair.getFirst().equals(playerIdPair.getSecond())) {
            throw new InvalidFriendshipException(
                    "A player cannot form a friendship with themselves. Both player IDs are: " + playerIdPair.getFirst()
            );
        }
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

    public List<DomainEvent> getEventStore() {
        return eventStore;
    }
}

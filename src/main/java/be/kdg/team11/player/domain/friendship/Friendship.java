package be.kdg.team11.player.domain.friendship;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;
import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipStateException;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipAcceptedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipRejectedEvent;
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
        validateFriendshipId(friendshipId);
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

        Friendship friendship = new Friendship(FriendshipId.create(),playerIdPair,FriendshipState.PENDING);

        FriendshipCreatedEvent event = new FriendshipCreatedEvent(
                friendship.friendshipId.friendshipId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                FriendshipState.PENDING.name()
        );
        friendship.eventStore.add(event);

        return friendship;
    }

/**
 * Transitions the friendship from PENDING to ACCEPTED state.
 * Can only be called when the friendship is in PENDING state.
 * Publishes a FriendshipAcceptedEvent to the event store.
 */
    public void accept() {
        if (this.friendshipState != FriendshipState.PENDING) {
            throw new InvalidFriendshipStateException(
                    "Cannot accept friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.ACCEPTED;

        FriendshipAcceptedEvent event = new FriendshipAcceptedEvent(
                this.friendshipId.friendshipId(),
                this.playerIdPair.getSecond().playerId()
        );

        this.eventStore.add(event);
    }
/**
 * Transitions the friendship from PENDING to REJECTED state.
 * Can only be called when the friendship is in PENDING state.
 * Publishes a FriendshipRejectedEvent to the event store.
 */
    public void reject() {
        if (this.friendshipState != FriendshipState.PENDING) {
            throw new InvalidFriendshipStateException(
                    "Cannot reject friendship: current state is " + this.friendshipState + ", expected PENDING"
            );
        }
        this.friendshipState = FriendshipState.REJECTED;
        FriendshipRejectedEvent event = new FriendshipRejectedEvent(
                this.friendshipId.friendshipId(),
                this.playerIdPair.getSecond().playerId()
        );

        this.eventStore.add(event);
    }

/**
 * Terminates an active friendship relationship.
 * Can only be called when the friendship is in ACCEPTED state.
 * Publishes a FriendshipRemovedEvent to the event store.
 */
    public void end(PlayerId initiatedBy) {
        if (!involvesPlayer(initiatedBy)) {
            throw new InvalidFriendshipException(
                    String.format("Player %s is not involved in this friendship and cannot remove it", initiatedBy.playerId())
            );
        }

        if (this.friendshipState != FriendshipState.ACCEPTED) {
            throw new InvalidFriendshipStateException(
                    "Cannot remove friendship: current state is " + this.friendshipState + ", expected ACCEPTED"
            );
        }

        this.friendshipState = FriendshipState.REJECTED;

        FriendshipEndEvent event = new FriendshipEndEvent(
                this.friendshipId.friendshipId(),
                initiatedBy.playerId()
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


    private static void validateFriendshipId(FriendshipId friendshipId) {
        if (friendshipId == null) {
            throw new InvalidFriendshipException("Friendship ID cannot be null");
        }
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

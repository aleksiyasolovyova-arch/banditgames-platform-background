package be.kdg.team11.player.domain.friendship.exceptions;

public class FriendRequestAlreadyExistsException extends FriendshipException {
    public FriendRequestAlreadyExistsException(String message) {
        super(message);
    }
}

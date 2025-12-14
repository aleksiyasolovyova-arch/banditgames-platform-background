package be.kdg.team11.player.domain.friendship.exeptions;

public class InvalidFriendshipStateException extends FriendshipException {
    public InvalidFriendshipStateException(String message) {
        super(message);
    }
}

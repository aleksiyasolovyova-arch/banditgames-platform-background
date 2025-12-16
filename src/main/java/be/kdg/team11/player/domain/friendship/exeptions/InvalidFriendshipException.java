package be.kdg.team11.player.domain.friendship.exeptions;

public class InvalidFriendshipException extends FriendshipException{
    public InvalidFriendshipException(String message) {
        super(message);
    }
}

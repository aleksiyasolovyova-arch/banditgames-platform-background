package be.kdg.team11.player.domain.friendship.exeptions;

public class InvalidFriendshipStateException extends RuntimeException {
    public InvalidFriendshipStateException(String message) {
        super(message);
    }
}

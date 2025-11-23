package team11.platform_backend.player.domain.friendship.exeptions;

public class InvalidFriendshipStateException extends RuntimeException {
    public InvalidFriendshipStateException(String message) {
        super(message);
    }
}

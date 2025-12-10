package be.kdg.team11.game.domain.game.exeptions;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException(String message) {
        super(message);
    }
}

package be.kdg.team11.content.domain.game.exeptions;

public class InvalidGameStateException extends GameException {
    public InvalidGameStateException(String message) {
        super(message);
    }
}

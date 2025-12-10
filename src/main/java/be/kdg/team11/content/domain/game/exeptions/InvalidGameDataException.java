package be.kdg.team11.content.domain.game.exeptions;

public class InvalidGameDataException extends GameException{
    public InvalidGameDataException(String message) {
        super(message);
    }
}

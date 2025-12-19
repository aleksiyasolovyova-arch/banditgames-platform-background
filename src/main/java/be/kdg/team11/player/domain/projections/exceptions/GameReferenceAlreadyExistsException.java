package be.kdg.team11.player.domain.projections.exceptions;

public class GameReferenceAlreadyExistsException extends GameReferenceException {
    public GameReferenceAlreadyExistsException(String message) {
        super(message);
    }
}

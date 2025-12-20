package be.kdg.team11.player.domain.projections.exceptions;

public class GameReferenceException extends RuntimeException {
    public GameReferenceException(String message) {
        super(message);
    }
}

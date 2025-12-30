package be.kdg.team11.player.domain.lobby.exceptions;

public class InvalidLobbyStateException extends LobbyException {
    public InvalidLobbyStateException(String message) {
        super(message);
    }

    public static InvalidLobbyStateException invalidStateTransition(
            String currentState,
            String expectedState,
            String operation) {
        String message = String.format(
                "Cannot %s: current state is %s, expected %s",
                operation,
                currentState,
                expectedState
        );
        return new InvalidLobbyStateException(message);
    }
}

package be.kdg.team11.player.domain.lobby.exceptions;

public class PlayerNotInLobbyException extends LobbyException {
    public PlayerNotInLobbyException(String message) {
        super(message);
    }
}

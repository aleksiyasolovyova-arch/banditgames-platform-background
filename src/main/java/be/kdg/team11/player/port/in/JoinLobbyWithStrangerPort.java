package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.lobby.Lobby;

import java.util.Optional;

public interface JoinLobbyWithStrangerPort {
    Optional<Lobby> joinLobbyWithStranger(JoinLobbyWithStrangerCommand command);
}

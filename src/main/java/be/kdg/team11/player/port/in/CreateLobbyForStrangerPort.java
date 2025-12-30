package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.lobby.Lobby;

public interface CreateLobbyForStrangerPort {
    Lobby createLobbyForStrangers(CreateLobbyForStrangerCommand command);
}

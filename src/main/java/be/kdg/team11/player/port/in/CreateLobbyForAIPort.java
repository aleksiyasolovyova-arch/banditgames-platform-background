package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.lobby.Lobby;

public interface CreateLobbyForAIPort {
    Lobby create(CreateLobbyForAICommand command);
}

package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.AvailableGame;

import java.util.Optional;

public interface JoinGameLobbyWithStrangerPort {
    Optional<Lobby> joinGameLobbyWithStranger(AvailableGame gameId, PlayerId playerId);
}

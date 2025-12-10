package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.gamelobby.GameLobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameId;


import java.util.Optional;

public interface JoinGameLobbyWithStrangerPort {
    Optional<GameLobby> joinGameLobbyWithStranger(GameId gameId, PlayerId playerId);
}

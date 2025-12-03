package team11.platform_backend.player.port.in;

import team11.platform_backend.player.domain.gamelobby.GameLobby;
import team11.platform_backend.player.domain.player.PlayerId;
import team11.platform_backend.player.domain.projections.GameId;


import java.util.Optional;

public interface JoinGameLobbyWithStrangerPort {
    Optional<GameLobby> joinGameLobbyWithStranger(GameId gameId, PlayerId playerId);
}

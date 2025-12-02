package team11.platform_backend.gamelobby.port.in;

import team11.platform_backend.gamelobby.domain.GameLobby;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;

import java.util.Optional;

public interface JoinMatchMakingPort {
    Optional<GameLobby> joinMatchMaking(GameId gameId, PlayerId playerId);
}

package team11.platform_backend.gamelobby.port.out;

import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;

import java.util.Optional;

public interface MatchmakingQueuePort {
    Optional<MatchDto> savePlayerAndMatch(GameId gameId, PlayerId playerId);
}


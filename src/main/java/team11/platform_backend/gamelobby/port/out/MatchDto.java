package team11.platform_backend.gamelobby.port.out;

import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;

public record MatchDto(
        GameId gameId,
        PlayerId player1,
        PlayerId player2
) {}


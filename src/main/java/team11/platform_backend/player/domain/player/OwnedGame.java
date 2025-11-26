package team11.platform_backend.player.domain.player;

import team11.platform_backend.player.domain.projections.GameId;

import java.time.LocalDate;

public record OwnedGame (
        GameId gameId,
        boolean favourite,
        LocalDate dateBought
) {

}

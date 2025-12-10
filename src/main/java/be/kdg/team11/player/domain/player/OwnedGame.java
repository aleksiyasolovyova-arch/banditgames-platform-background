package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameId;

import java.time.LocalDate;

public record OwnedGame (
        GameId gameId,
        boolean favourite,
        LocalDate dateBought
) {

}

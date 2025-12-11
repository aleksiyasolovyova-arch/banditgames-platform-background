package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDate;

public record OwnedGame(
        GameReference gameReference,
        boolean favourite,
        LocalDate dateBought
) {

}

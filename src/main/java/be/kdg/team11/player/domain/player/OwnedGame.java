package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.projections.GameReference;

import java.time.LocalDate;

//TODO This is not a value object! It has a mutable value. Figure it out please!

public record OwnedGame(
        GameReference gameReference,
        boolean favourite,
        LocalDate dateBought
) {

}

package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.projections.GameReference;

public interface SaveGameReferencePort {
    GameReference save(GameReference gameReference);
}

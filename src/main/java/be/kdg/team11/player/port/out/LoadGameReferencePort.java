package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.projections.GameReference;

import java.util.List;

public interface LoadGameReferencePort {
    List<GameReference> loadAll();
}

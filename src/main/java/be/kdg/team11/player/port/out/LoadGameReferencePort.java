package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.projections.GameReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadGameReferencePort {
    List<GameReference> loadAll();
    Optional<GameReference> loadBy(UUID gameId);
}

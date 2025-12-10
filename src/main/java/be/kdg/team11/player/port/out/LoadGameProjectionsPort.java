package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.projections.GameProjection;

import java.util.List;

public interface LoadGameProjectionsPort {
    List<GameProjection> loadAll();
}

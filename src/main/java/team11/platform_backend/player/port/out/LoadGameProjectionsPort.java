package team11.platform_backend.player.port.out;

import team11.platform_backend.player.domain.projections.GameProjection;

import java.util.List;

public interface LoadGameProjectionsPort {
    List<GameProjection> loadAllGameProjections();
}

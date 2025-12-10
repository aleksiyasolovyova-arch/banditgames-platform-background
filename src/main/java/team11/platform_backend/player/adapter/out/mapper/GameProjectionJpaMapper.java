package team11.platform_backend.player.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.player.adapter.out.jpa.entity.GameProjectionJpaEntity;
import team11.platform_backend.player.domain.projections.GameId;
import team11.platform_backend.player.domain.projections.GameProjection;

@Component
public class GameProjectionJpaMapper {
    public GameProjection toDomain(GameProjectionJpaEntity entity) {
        return new GameProjection(
                new GameId(entity.getGameId())
        );
    }

    public GameProjectionJpaEntity toJpaEntity(GameProjection projection) {
        return new GameProjectionJpaEntity(
                projection.getGameId().gameId()
        );
    }
}

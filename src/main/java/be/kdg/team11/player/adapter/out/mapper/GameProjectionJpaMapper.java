package be.kdg.team11.player.adapter.out.mapper;

import org.springframework.stereotype.Component;
import be.kdg.team11.player.adapter.out.jpa.entity.GameProjectionJpaEntity;
import be.kdg.team11.player.domain.projections.GameId;
import be.kdg.team11.player.domain.projections.GameProjection;

@Component
public class GameProjectionJpaMapper {
    public GameProjection toDomain(GameProjectionJpaEntity entity) {
        return new GameProjection(
                new GameId(entity.getGameId())
        );
    }

    public GameProjectionJpaEntity toJpaEntity(GameProjection projection) {
        return new GameProjectionJpaEntity(
                projection.gameId().gameId()
        );
    }
}

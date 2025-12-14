package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.entity.GameReferenceJpaEntity;
import be.kdg.team11.player.domain.projections.AvailableGame;
import org.springframework.stereotype.Component;

@Component
public class GameReferenceJpaMapper {
    public GameProjection toDomain(GameReferenceJpaEntity entity) {
        return new GameProjection(
                new AvailableGame(entity.getGameId())
        );
    }

    public GameReferenceJpaEntity toJpaEntity(GameProjection projection) {
        return new GameReferenceJpaEntity(
                projection.gameId().gameId()
        );
    }
}

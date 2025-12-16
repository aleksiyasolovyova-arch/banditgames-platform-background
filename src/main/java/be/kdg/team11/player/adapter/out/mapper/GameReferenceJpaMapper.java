package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.entity.GameReferenceJpaEntity;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.stereotype.Component;

@Component
public class GameReferenceJpaMapper {
    public GameReference toDomain(GameReferenceJpaEntity entity) {
        return new GameReference(
                entity.getGameId()
        );
    }

    public GameReferenceJpaEntity toJpaEntity(GameReference gameReference) {
        GameReferenceJpaEntity entity = new GameReferenceJpaEntity();
        entity.setGameId(gameReference.gameId());
        return entity;
    }
}

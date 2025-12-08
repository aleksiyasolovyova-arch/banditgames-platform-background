package team11.platform_backend.player.adapter.out.jpa;

import org.springframework.stereotype.Component;
import team11.platform_backend.player.domain.player.Player;
import team11.platform_backend.player.domain.player.PlayerId;

@Component
public class PlayerJpaMapper {
    public Player toDomain(PlayerJpaEntity entity) {
        PlayerId playerId = new PlayerId(entity.getPlayerId());
        return new Player(playerId, entity.getJoinedDate(), java.util.Collections.emptyList(), java.util.Collections.emptyList());
    }

    public PlayerJpaEntity toJpaEntity(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().playerId(),
                player.getJoinedDate()
        );
    }
}

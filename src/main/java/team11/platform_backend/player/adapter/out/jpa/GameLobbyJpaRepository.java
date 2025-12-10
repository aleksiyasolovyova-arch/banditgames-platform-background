package team11.platform_backend.player.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import team11.platform_backend.player.adapter.out.jpa.entity.GameLobbyJpaEntity;

import java.util.UUID;

public interface GameLobbyJpaRepository extends JpaRepository<GameLobbyJpaEntity, UUID> {
}

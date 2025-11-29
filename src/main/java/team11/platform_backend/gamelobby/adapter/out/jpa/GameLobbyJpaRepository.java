package team11.platform_backend.gamelobby.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameLobbyJpaRepository extends JpaRepository<GameLobbyJpaEntity, UUID> {
}

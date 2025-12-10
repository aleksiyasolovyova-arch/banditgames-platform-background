package be.kdg.team11.player.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import be.kdg.team11.player.adapter.out.jpa.entity.GameLobbyJpaEntity;

import java.util.UUID;

public interface GameLobbyJpaRepository extends JpaRepository<GameLobbyJpaEntity, UUID> {
}

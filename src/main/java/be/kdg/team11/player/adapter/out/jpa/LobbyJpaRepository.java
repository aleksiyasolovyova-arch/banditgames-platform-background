package be.kdg.team11.player.adapter.out.jpa;

import be.kdg.team11.player.adapter.out.jpa.entity.LobbyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {
}

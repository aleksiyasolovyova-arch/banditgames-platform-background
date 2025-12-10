package be.kdg.team11.player.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.kdg.team11.player.adapter.out.jpa.entity.PlayerJpaEntity;

import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
}

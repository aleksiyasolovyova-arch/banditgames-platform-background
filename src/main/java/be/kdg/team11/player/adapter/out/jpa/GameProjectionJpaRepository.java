package be.kdg.team11.player.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.kdg.team11.player.adapter.out.jpa.entity.GameProjectionJpaEntity;

import java.util.UUID;

@Repository
public interface GameProjectionJpaRepository extends JpaRepository<GameProjectionJpaEntity, UUID> {
}

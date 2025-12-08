package team11.platform_backend.player.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameProjectionJpaRepository extends JpaRepository<GameProjectionJpaEntity, UUID> {
}

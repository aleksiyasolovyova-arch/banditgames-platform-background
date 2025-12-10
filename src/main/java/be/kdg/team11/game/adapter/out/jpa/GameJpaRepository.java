package be.kdg.team11.game.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
}

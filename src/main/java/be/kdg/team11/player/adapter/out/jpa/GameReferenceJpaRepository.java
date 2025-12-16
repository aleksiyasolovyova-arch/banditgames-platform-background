package be.kdg.team11.player.adapter.out.jpa;

import be.kdg.team11.player.adapter.out.jpa.entity.GameReferenceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameReferenceJpaRepository extends JpaRepository<GameReferenceJpaEntity, UUID> {
}

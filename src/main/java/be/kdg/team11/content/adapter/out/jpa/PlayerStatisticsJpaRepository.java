package be.kdg.team11.content.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerStatisticsJpaRepository extends JpaRepository<PlayerStatisticsJpaEntity, UUID> {
}

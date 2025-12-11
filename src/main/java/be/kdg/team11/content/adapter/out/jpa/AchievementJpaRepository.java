package be.kdg.team11.content.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, UUID> {
}

package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.UnlockedAchievementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface UnlockedAchievementModelRepository extends JpaRepository<UnlockedAchievementModel, UUID> {
    List<UnlockedAchievementModel> findByPlayerId(UUID playerId);
}

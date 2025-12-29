package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.AchievementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface AchievementModelRepository extends JpaRepository<AchievementModel, UUID> {
    List<AchievementModel> findByPlayerId(UUID playerId);
}

package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.AchievementModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AchievementModelRepository extends JpaRepository<AchievementModel, UUID> {
    List<AchievementModel> findByGameIdIsNull();
    List<AchievementModel> findByGameId(UUID gameId);
    List<AchievementModel> findByType(AchievementModelType type);
}

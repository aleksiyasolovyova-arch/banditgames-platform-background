package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.PlatformAchievementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlatformAchievementModelRepository extends JpaRepository<PlatformAchievementModel, UUID> {
    List<PlatformAchievementModel> findByGameIdIsNull();
    List<PlatformAchievementModel> findByGameId(UUID gameId);
    List<PlatformAchievementModel> findByType(AchievementModelType type);
}

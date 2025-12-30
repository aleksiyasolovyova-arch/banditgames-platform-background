package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.PlatformAchievementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlatformAchievementModelRepository extends JpaRepository<PlatformAchievementModel, UUID> {
}

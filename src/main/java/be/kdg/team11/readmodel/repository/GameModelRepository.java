package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameModelRepository extends JpaRepository<GameModel, UUID> {
    @Query("SELECT DISTINCT g FROM GameModel g " +
            "LEFT JOIN FETCH RuleModel r on r.gameId = g.gameId " +
            "LEFT JOIN FETCH AchievementModel a on a.gameId = g.gameId")
    List<GameModel> findAllWithRulesAndAchievements();

    // Fetch games with only rules
    @Query("SELECT DISTINCT g FROM GameModel g " +
            "LEFT JOIN FETCH RuleModel r on r.gameId = g.gameId")
    List<GameModel> findAllWithRules();
}

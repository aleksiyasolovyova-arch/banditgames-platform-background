package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.RuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RuleModelRepository extends JpaRepository<RuleModel, UUID> {
    List<RuleModel> findAllByGameId(UUID gameId);
    void deleteAllByGameId(UUID gameId);
}

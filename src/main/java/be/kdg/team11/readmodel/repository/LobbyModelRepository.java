package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.LobbyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LobbyModelRepository extends JpaRepository<LobbyModel, UUID> {
    List<LobbyModel> findByPlayer1IdOrPlayer2IdOrderByFinishedAtDesc(UUID player1Id, UUID player2Id);
}

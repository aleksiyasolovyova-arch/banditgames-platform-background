package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.LobbyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LobbyModelRepository extends JpaRepository<LobbyModel, UUID> {
}

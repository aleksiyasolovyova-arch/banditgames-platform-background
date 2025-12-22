package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.models.PlayerModel;

import java.util.Optional;
import java.util.UUID;

public interface PlayerModelService {
    void project (UUID playerId, UUID gameId);
    void project (UUID playerId);
    Optional<PlayerModel> findByPlayerId(UUID playerId);
}

package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.models.PlayerModel;

import java.util.Optional;
import java.util.UUID;

public interface PlayerModelService {
    //TODO take event for projection methods and write logic in implementation
    void project (UUID playerId, UUID gameId);
    void project (UUID playerId);

    //TODO return dto instead of object and do mapping logic in service!
    Optional<PlayerModel> findByPlayerId(UUID playerId);
}

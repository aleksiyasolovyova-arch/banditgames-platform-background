package be.kdg.team11.readmodel.service.playerfavouritegame;

import java.util.UUID;

public interface PlayerFavouriteGameService {
    void project (UUID playerId, UUID gameId);
    void project (UUID playerId);
}

package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedPictureUrlEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerCreatedEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;

import java.util.Optional;
import java.util.UUID;

public interface PlayerModelService {
    void project(PlayerCreatedEvent event);
    void project(PlayerChangedPictureUrlEvent event);
    void project(PlayerChangedFavoriteGameEvent event);
    void project(PlayerRemovedFavoriteGameEvent event);

    //TODO return dto instead of object and do mapping logic in service!
    Optional<PlayerModel> findByPlayerId(UUID playerId);
}

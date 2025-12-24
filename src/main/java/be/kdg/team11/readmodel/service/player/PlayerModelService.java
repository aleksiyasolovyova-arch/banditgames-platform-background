package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.controller.dto.PlayerModelDto;
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

    Optional<PlayerModelDto> findByPlayerId(UUID playerId);
}

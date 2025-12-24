package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.player.PlayerModelService;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedPictureUrlEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerCreatedEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PlayerEventListener {
    private final PlayerModelService playerModelService;

    public PlayerEventListener(PlayerModelService playerModelService) {
        this.playerModelService = playerModelService;
    }

    @EventListener(PlayerCreatedEvent.class)
    public void playerCreated(PlayerCreatedEvent event) {
        playerModelService.project(event);
    }

    @EventListener(PlayerChangedFavoriteGameEvent.class)
    public void playerChangedFavoriteGame(PlayerChangedFavoriteGameEvent event) {
        playerModelService.project(event);
    }

    @EventListener(PlayerRemovedFavoriteGameEvent.class)
    public void playerRemovedFavoriteGame(PlayerRemovedFavoriteGameEvent event) {
        playerModelService.project(event);
    }

    @EventListener(PlayerChangedPictureUrlEvent.class)
    public void playerChangedPictureUrl(PlayerChangedPictureUrlEvent event) {
        playerModelService.project(event);
    }
}

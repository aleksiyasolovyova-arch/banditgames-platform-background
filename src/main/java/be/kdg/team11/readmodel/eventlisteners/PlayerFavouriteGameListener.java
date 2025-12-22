package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.player.PlayerModelService;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;
import org.springframework.context.event.EventListener;

public class PlayerFavouriteGameListener {
    private final PlayerModelService playerModelService;

    public PlayerFavouriteGameListener(PlayerModelService playerModelService) {
        this.playerModelService = playerModelService;
    }

    //TODO move this logic to the service ( just pass the event as a attribute )

    @EventListener(PlayerChangedFavoriteGameEvent.class)
    public void playerChangedFavoriteGame(PlayerChangedFavoriteGameEvent event){
        playerModelService.project(event.playerId(), event.gameId());
    }

    @EventListener(PlayerRemovedFavoriteGameEvent.class)
    public void playerRemovedFavoriteGame(PlayerRemovedFavoriteGameEvent event){
        playerModelService.project(event.playerId());
    }
}

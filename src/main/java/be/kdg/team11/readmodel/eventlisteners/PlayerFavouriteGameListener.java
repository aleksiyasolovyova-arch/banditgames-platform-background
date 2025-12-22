package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.playerfavouritegame.PlayerFavouriteGameService;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;
import org.springframework.context.event.EventListener;

public class PlayerFavouriteGameListener {
    private final PlayerFavouriteGameService playerFavouriteGameService;

    public PlayerFavouriteGameListener(PlayerFavouriteGameService playerFavouriteGameService) {
        this.playerFavouriteGameService = playerFavouriteGameService;
    }

    @EventListener(PlayerChangedFavoriteGameEvent.class)
    public void playerChangedFavoriteGame(PlayerChangedFavoriteGameEvent event){
        playerFavouriteGameService.project(event.playerId(), event.gameId());
    }

    @EventListener(PlayerRemovedFavoriteGameEvent.class)
    public void playerRemovedFavoriteGame(PlayerRemovedFavoriteGameEvent event){
        playerFavouriteGameService.project(event.playerId());
    }
}

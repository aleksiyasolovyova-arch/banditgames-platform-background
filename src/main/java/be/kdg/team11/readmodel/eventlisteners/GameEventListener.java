package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.game.GameModelService;
import be.kdg.team11.sharedkernel.events.game.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {
    private final GameModelService gameModelService;

    public GameEventListener(GameModelService gameModelService) {
        this.gameModelService = gameModelService;
    }

    @EventListener(GameRegisteredEvent.class)
    public void gameRegistered(GameRegisteredEvent event) {
        gameModelService.project(event);
    }


    @EventListener(PassedGameReviewEvent.class)
    public void gamePassedReview(PassedGameReviewEvent event) {
        gameModelService.project(event);
    }

    @EventListener(FailedGameReviewEvent.class)
    public void gameFailedReview(FailedGameReviewEvent event) {
        gameModelService.project(event);
    }

    @EventListener(GameToggledPlayableWithAIEvent.class)
    public void gameToggledPlayableWithAI(GameToggledPlayableWithAIEvent event) {
        gameModelService.project(event);
    }

    @EventListener(GameUrlsModifiedEvent.class)
    public void gameUrlsModified(GameUrlsModifiedEvent event) {
        gameModelService.project(event);
    }
}
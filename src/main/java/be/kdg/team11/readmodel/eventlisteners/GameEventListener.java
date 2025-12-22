package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.models.GameModelAchievementEmbeddable;
import be.kdg.team11.readmodel.service.game.GameModelService;
import be.kdg.team11.sharedkernel.events.game.GameRegisteredEvent;
import be.kdg.team11.sharedkernel.events.game.PassedGameReviewEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {
    private final GameModelService gameModelService;

    public GameEventListener(GameModelService gameModelService) {
        this.gameModelService = gameModelService;
    }

    //TODO move this logic to the service ( just pass the event as a attribute )
    @EventListener(GameRegisteredEvent.class)
    public void gameRegistered(GameRegisteredEvent event){
        gameModelService.project(
                event.gameId(),
                event.name(),
                event.description(),
                event.pictureUrl(),
                event.gameUrl(),
                event.gameCreatorName(),
                event.initialReviewStatus(),
                event.rules().stream().map(GameRegisteredEvent.RuleRecord::description).toList(),
                event.achievements().stream()
                        .map(a -> {
                            GameModelAchievementEmbeddable embeddable = new GameModelAchievementEmbeddable();
                            embeddable.setCode(a.code());
                            embeddable.setDescription(a.description());
                            return embeddable;
                        })
                        .toList(),
                event.playableWithAI()
        );
    }


    @EventListener(PassedGameReviewEvent.class)
    public void gamePassedReview(PassedGameReviewEvent event) {

    }
}
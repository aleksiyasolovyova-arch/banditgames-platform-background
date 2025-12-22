package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.game.GameService;
import be.kdg.team11.sharedkernel.events.game.PassedGameReviewEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {
    private final GameService gameService;

    public GameEventListener(GameService gameService) {
        this.gameService = gameService;
    }

    @EventListener(PassedGameReviewEvent.class)
    public void gamePassedReview(PassedGameReviewEvent event) {
        gameService.project(
                event.gameId(),
                event.name(),
                event.description(),
                event.pictureUrl(),
                event.gameUrl(),
                event.gameCreatorName(),
                event.rules().stream()
                        .map(PassedGameReviewEvent.RuleRecord::description)
                        .toList(),
                event.playableWithAI()
        );
    }
}
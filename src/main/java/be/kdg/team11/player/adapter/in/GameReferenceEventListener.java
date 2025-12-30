package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.port.in.GameReferenceCommand;
import be.kdg.team11.player.port.in.GameReferenceProjector;
import be.kdg.team11.sharedkernel.events.game.PassedGameReviewEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameReferenceEventListener {
    private final GameReferenceProjector gameReferenceProjector;

    public GameReferenceEventListener(GameReferenceProjector gameReferenceProjector) {
        this.gameReferenceProjector = gameReferenceProjector;
    }

    @EventListener(PassedGameReviewEvent.class)
    public void gameReviewPassed(PassedGameReviewEvent event) {
        gameReferenceProjector.project(new GameReferenceCommand(event.gameId(), event.gameUrl()));
    }
}

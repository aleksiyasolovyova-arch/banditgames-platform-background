package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.out.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GameEventPublisher implements SaveGamePort{
    private final ApplicationEventPublisher applicationEventPublisher;

    public GameEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Game save(Game game) {
        game.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return game;
    }

}

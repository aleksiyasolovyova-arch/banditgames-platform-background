package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.out.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ContentEventPublisher implements SaveGamePort, SaveAchievementPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public ContentEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //TODO figure out
   // @Override
   // public void delete(Game game) {
   //
   // }
    //if the game is deleted before the event was published it will gve an error

    @Override
    public Achievement save(Achievement achievement) {
        achievement.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return achievement;
    }

    @Override
    public Game save(Game game) {
        game.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return game;
    }

}

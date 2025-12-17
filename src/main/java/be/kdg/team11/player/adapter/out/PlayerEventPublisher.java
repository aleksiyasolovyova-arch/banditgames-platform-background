package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.out.SavePlayerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PlayerEventPublisher implements SavePlayerPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PlayerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Player save(Player player) {
        player.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return player;
    }
}

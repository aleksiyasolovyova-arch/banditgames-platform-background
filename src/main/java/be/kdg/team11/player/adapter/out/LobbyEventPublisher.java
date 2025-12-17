package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LobbyEventPublisher implements SaveLobbyPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LobbyEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Lobby save(Lobby lobby) {
        lobby.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return lobby;
    }
}
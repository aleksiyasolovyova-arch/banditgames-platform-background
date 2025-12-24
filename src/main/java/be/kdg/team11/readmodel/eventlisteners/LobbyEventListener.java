package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.lobby.LobbyModelService;
import be.kdg.team11.sharedkernel.events.lobby.LobbyCreatedEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyEventListener {
    private final LobbyModelService lobbyModelService;

    public LobbyEventListener(LobbyModelService lobbyModelService) {
        this.lobbyModelService = lobbyModelService;
    }

    @EventListener(LobbyCreatedEvent.class)
    public void lobbyCreated(LobbyCreatedEvent event) {
        lobbyModelService.project(event);
    }


    @EventListener(LobbyStartedEvent.class)
    public void lobbyStarted(LobbyStartedEvent event) {
        lobbyModelService.project(event);
    }

    @EventListener(LobbyEndedWithWinnerEvent.class)
    public void lobbyEndedWithWinner(LobbyEndedWithWinnerEvent event) {
        lobbyModelService.project(event);
    }

    @EventListener(LobbyEndedWithDrawEvent.class)
    public void lobbyEndedWithDraw(LobbyEndedWithDrawEvent event) {
        lobbyModelService.project(event);
    }
}

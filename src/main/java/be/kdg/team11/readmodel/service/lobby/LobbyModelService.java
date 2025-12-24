package be.kdg.team11.readmodel.service.lobby;

import be.kdg.team11.sharedkernel.events.lobby.LobbyCreatedEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyStartedEvent;

public interface LobbyModelService {
    void project(LobbyCreatedEvent event);
    void project(LobbyEndedWithDrawEvent event);
    void project(LobbyEndedWithWinnerEvent event);
    void project(LobbyStartedEvent event);
}

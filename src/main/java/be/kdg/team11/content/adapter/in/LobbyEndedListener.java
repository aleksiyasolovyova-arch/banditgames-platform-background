package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.port.in.LobbyEndedWithDrawCommand;
import be.kdg.team11.content.port.in.LobbyEndedWithDrawProjector;
import be.kdg.team11.content.port.in.LobbyEndedWithWinnerCommand;
import be.kdg.team11.content.port.in.LobbyEndedWithWinnerProjector;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyEndedListener {
    private final LobbyEndedWithWinnerProjector lobbyEndedWithWinnerProjector;
    private final LobbyEndedWithDrawProjector lobbyEndedWithDrawProjector;

    public LobbyEndedListener(LobbyEndedWithWinnerProjector lobbyEndedWithWinnerProjector, LobbyEndedWithDrawProjector lobbyEndedWithDrawProjector) {
        this.lobbyEndedWithWinnerProjector = lobbyEndedWithWinnerProjector;
        this.lobbyEndedWithDrawProjector = lobbyEndedWithDrawProjector;
    }

    @EventListener(LobbyEndedWithDrawEvent.class)
    public void lobbyEndedWithDraw(LobbyEndedWithDrawEvent event){
        lobbyEndedWithDrawProjector.project(new LobbyEndedWithDrawCommand(event.lobbyId(),event.player1Id(),event.player2Id(),event.time(),event.eventPit()));
    }

    @EventListener(LobbyEndedWithWinnerEvent.class)
    public void lobbyEndedWithWinner(LobbyEndedWithWinnerEvent event){
        lobbyEndedWithWinnerProjector.project(new LobbyEndedWithWinnerCommand(event.lobbyId(),event.winnerId(),event.player1Id(),event.player2Id(),event.time(),event.eventPit()));

    }
}

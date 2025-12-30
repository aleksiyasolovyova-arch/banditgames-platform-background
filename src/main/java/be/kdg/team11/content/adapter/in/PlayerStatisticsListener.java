package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.port.in.*;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatisticsListener {
    private final LobbyEndedWithWinnerProjector lobbyEndedWithWinnerProjector;
    private final LobbyEndedWithDrawProjector lobbyEndedWithDrawProjector;
    private final PlayerBefriendedProjector playerBefriendedProjector;

    public PlayerStatisticsListener(LobbyEndedWithWinnerProjector lobbyEndedWithWinnerProjector, LobbyEndedWithDrawProjector lobbyEndedWithDrawProjector, PlayerBefriendedProjector playerBefriendedProjector) {
        this.lobbyEndedWithWinnerProjector = lobbyEndedWithWinnerProjector;
        this.lobbyEndedWithDrawProjector = lobbyEndedWithDrawProjector;
        this.playerBefriendedProjector = playerBefriendedProjector;
    }

    @EventListener(LobbyEndedWithDrawEvent.class)
    public void lobbyEndedWithDraw(LobbyEndedWithDrawEvent event) {
        lobbyEndedWithDrawProjector.project(new LobbyEndedWithDrawCommand(event.lobbyId(), event.player1Id(), event.player2Id(), event.time(), event.eventPit()));
    }

    @EventListener(LobbyEndedWithWinnerEvent.class)
    public void lobbyEndedWithWinner(LobbyEndedWithWinnerEvent event) {
        lobbyEndedWithWinnerProjector.project(new LobbyEndedWithWinnerCommand(event.lobbyId(), event.winnerId(), event.player1Id(), event.player2Id(), event.time(), event.eventPit()));

    }

    @EventListener(BefriendedPlayerEvent.class)
    public void playerBefriended(BefriendedPlayerEvent event) {
        playerBefriendedProjector.project(new PlayerBefriendedCommand(event.requesterId(),event.recipientId()));
    }
}

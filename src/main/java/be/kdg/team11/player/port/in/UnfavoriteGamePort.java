package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.Player;

public interface UnfavoriteGamePort {
    Player unfavoriteGame(UnfavoriteGameCommand command);
}

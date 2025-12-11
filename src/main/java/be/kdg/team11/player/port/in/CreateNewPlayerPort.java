package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.Player;

public interface CreateNewPlayerPort {
    Player createNewPlayer(CreateNewPlayerCommand command);
}

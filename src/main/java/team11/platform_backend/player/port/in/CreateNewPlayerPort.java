package team11.platform_backend.player.port.in;

import team11.platform_backend.player.domain.player.Player;

public interface CreateNewPlayerPort {
    Player createNewPlayer(CreateNewPlayerCommand command);
}

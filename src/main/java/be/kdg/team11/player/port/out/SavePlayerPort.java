package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.player.Player;

public interface SavePlayerPort {
    Player save(Player player);
}

package team11.platform_backend.player.port.out;

import team11.platform_backend.player.domain.player.Player;

public interface SavePlayerPort {
    Player save(Player player);
}

package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;

import java.util.Optional;

public interface LoadPlayerPort {
    Optional<Player> loadBy(PlayerId playerId);
}

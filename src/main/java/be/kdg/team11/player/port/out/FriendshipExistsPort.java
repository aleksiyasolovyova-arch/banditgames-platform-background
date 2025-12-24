package be.kdg.team11.player.port.out;

import be.kdg.team11.player.domain.player.PlayerId;
import org.springframework.data.util.Pair;

public interface FriendshipExistsPort {
    boolean exists(Pair<PlayerId, PlayerId> playerIdPair);
}

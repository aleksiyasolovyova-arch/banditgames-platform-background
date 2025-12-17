package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.RequestFriendshipCommand;
import be.kdg.team11.player.port.in.RequestFriendshipPort;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Use case implementation for requesting a friendship between two players.
 */
@Service
@Transactional
public class RequestFriendshipUseCaseImpl implements RequestFriendshipPort {
    private final List<SaveFriendshipPort> saveFriendshipPorts;

    public RequestFriendshipUseCaseImpl(List<SaveFriendshipPort> saveFriendshipPorts) {
        this.saveFriendshipPorts = saveFriendshipPorts;
    }

    @Override
    public Friendship requestFriendship(RequestFriendshipCommand command) {
        PlayerId requesterId = PlayerId.of(command.requesterId());
        PlayerId recipientId = PlayerId.of(command.recipientId());

        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(requesterId, recipientId);

        Friendship friendship = Friendship.create(playerIdPair);

        saveFriendshipPorts.forEach(port -> port.save(friendship));

        return friendship;
    }

}

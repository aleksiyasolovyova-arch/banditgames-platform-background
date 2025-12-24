package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.player.Username;
import be.kdg.team11.player.port.in.RequestFriendshipCommand;
import be.kdg.team11.player.port.in.RequestFriendshipPort;
import be.kdg.team11.player.port.out.FriendshipExistsPort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
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
    private final LoadPlayerPort loadPlayerPort;
    private final List<SaveFriendshipPort> saveFriendshipPorts;
    private final FriendshipExistsPort friendshipExistsPort;

    public RequestFriendshipUseCaseImpl(LoadPlayerPort loadPlayerPort, List<SaveFriendshipPort> saveFriendshipPorts, FriendshipExistsPort friendshipExistsPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.friendshipExistsPort = friendshipExistsPort;
        this.saveFriendshipPorts = saveFriendshipPorts;
    }

    @Override
    public Friendship requestFriendship(RequestFriendshipCommand command) {
        PlayerId requesterId = PlayerId.of(command.requesterId());
        Player recipient = loadPlayerPort.loadBy(command.recipientUsername()).orElseThrow(() -> Username.notFound(command.recipientUsername()));

        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(requesterId, recipient.getPlayerId());

        if(friendshipExistsPort.exists(playerIdPair)){
           throw FriendshipId.alreadyExists();
        }

        Friendship friendship = Friendship.create(playerIdPair);
        saveFriendshipPorts.forEach(port -> port.save(friendship));

        return friendship;
    }

}

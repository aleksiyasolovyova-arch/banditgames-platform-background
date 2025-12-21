package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.EndFriendshipCommand;
import be.kdg.team11.player.port.in.EndFriendshipPort;
import be.kdg.team11.player.port.out.LoadFriendshipPort;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EndFriendshipUseCaseImpl implements EndFriendshipPort {
    private final LoadFriendshipPort loadFriendshipPort;
    private final List<SaveFriendshipPort> saveFriendshipPorts;

    public EndFriendshipUseCaseImpl(
            LoadFriendshipPort loadFriendshipPort,
            List<SaveFriendshipPort> saveFriendshipPorts
    ) {
        this.loadFriendshipPort = loadFriendshipPort;
        this.saveFriendshipPorts = saveFriendshipPorts;
    }

    @Override
    public Friendship endFriendship(EndFriendshipCommand command) {
        FriendshipId friendshipId = FriendshipId.of(command.friendshipId());
        PlayerId initiatedByPlayerId = PlayerId.of(command.initiatedBy());

        Friendship friendship = loadFriendshipPort.loadBy(friendshipId).orElseThrow(() -> FriendshipId.notFound(friendshipId));
        friendship.end(initiatedByPlayerId);

        saveFriendshipPorts.forEach(port -> port.save(friendship));

        return friendship;
    }

}

package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.port.in.BefriendPlayerCommand;
import be.kdg.team11.player.port.in.BefriendPlayerPort;
import be.kdg.team11.player.port.out.LoadFriendshipPort;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BefriendPlayerUseCaseImpl implements BefriendPlayerPort {
    private final LoadFriendshipPort loadFriendshipPort;
    private final List<SaveFriendshipPort> saveFriendshipPorts;

    public BefriendPlayerUseCaseImpl(
            LoadFriendshipPort loadFriendshipPort,
            List<SaveFriendshipPort> saveFriendshipPorts
    ) {
        this.loadFriendshipPort = loadFriendshipPort;
        this.saveFriendshipPorts = saveFriendshipPorts;
    }

    @Override
    public Friendship befriendPlayer(BefriendPlayerCommand command) {
        FriendshipId friendshipId = FriendshipId.of(command.friendshipId());

        Friendship friendship = loadFriendshipPort.loadBy(friendshipId).orElseThrow(() -> FriendshipId.notFound(friendshipId));
        friendship.befriend();

        saveFriendshipPorts.forEach(port -> port.save(friendship));

        return friendship;
    }
}

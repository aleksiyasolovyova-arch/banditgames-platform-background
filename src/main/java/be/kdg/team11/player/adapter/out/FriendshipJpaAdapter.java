package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.FriendshipJpaRepository;
import be.kdg.team11.player.adapter.out.mapper.FriendshipJpaMapper;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.out.FriendshipExistsPort;
import be.kdg.team11.player.port.out.LoadFriendshipPort;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FriendshipJpaAdapter implements LoadFriendshipPort, SaveFriendshipPort, FriendshipExistsPort {
    private final FriendshipJpaRepository friendshipJpaRepository;
    private final FriendshipJpaMapper friendshipJpaMapper;

    public FriendshipJpaAdapter(
            FriendshipJpaRepository friendshipJpaRepository,
            FriendshipJpaMapper friendshipJpaMapper
    ) {
        this.friendshipJpaRepository = friendshipJpaRepository;
        this.friendshipJpaMapper = friendshipJpaMapper;
    }


    @Override
    public Optional<Friendship> loadBy(FriendshipId friendshipId) {
        return friendshipJpaRepository.findById(friendshipId.friendshipId()).map(friendshipJpaMapper::toDomain);
    }

    @Override
    public Friendship save(Friendship friendship) {
        return friendshipJpaMapper.toDomain(friendshipJpaRepository.save(friendshipJpaMapper.toJpaEntity(friendship)));
    }

    @Override
    public boolean exists(Pair<PlayerId, PlayerId> playerIdPair) {
        return friendshipJpaRepository.existsBetweenPlayers(playerIdPair.getFirst().playerId(), playerIdPair.getSecond().playerId());
    }
}

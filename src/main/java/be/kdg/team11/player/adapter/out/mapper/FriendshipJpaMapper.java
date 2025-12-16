package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.entity.FriendshipJpaEntity;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.PlayerId;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class FriendshipJpaMapper {
    public Friendship toDomain(FriendshipJpaEntity entity){
        return new Friendship(
                new FriendshipId(entity.getFriendShipId()),
                Pair.of(new PlayerId(entity.getRequesterId()), new PlayerId(entity.getRecipientId())),
                entity.getFriendshipState()
        );
    }
    public FriendshipJpaEntity toJpaEntity (Friendship friendship){
        FriendshipJpaEntity friendshipJpaEntity = new FriendshipJpaEntity();

        friendshipJpaEntity.setFriendShipId(friendship.getFriendshipId().friendshipId());
        friendshipJpaEntity.setRequesterId(friendship.getRequester().playerId());
        friendshipJpaEntity.setRecipientId(friendship.getRecipient().playerId());
        friendshipJpaEntity.setFriendshipState(friendship.getFriendshipState());

        return friendshipJpaEntity;
    }
}

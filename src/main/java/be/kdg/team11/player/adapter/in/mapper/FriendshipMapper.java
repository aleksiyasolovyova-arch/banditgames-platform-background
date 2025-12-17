package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.request.RequestFriendshipRequest;
import be.kdg.team11.player.adapter.in.response.FriendshipDto;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.port.in.RequestFriendshipCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipMapper {

    public RequestFriendshipCommand toCommand(UUID requesterId, RequestFriendshipRequest request) {
        return new RequestFriendshipCommand(
                requesterId,
                request.recipientId()
        );
    }

    public FriendshipDto toResponse(Friendship friendship) {
        return new FriendshipDto(
                friendship.getFriendshipId().friendshipId(),
                friendship.getRequester().playerId(),
                friendship.getRecipient().playerId(),
                friendship.getFriendshipState().name()
        );
    }

}

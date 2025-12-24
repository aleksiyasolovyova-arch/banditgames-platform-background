package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.request.RequestFriendshipRequest;
import be.kdg.team11.player.adapter.in.response.FriendshipDto;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.port.in.BefriendPlayerCommand;
import be.kdg.team11.player.port.in.DeclineFriendshipCommand;
import be.kdg.team11.player.port.in.EndFriendshipCommand;
import be.kdg.team11.player.port.in.RequestFriendshipCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipMapper {

    public RequestFriendshipCommand toRequestCommand(UUID requesterId, RequestFriendshipRequest request) {
        return new RequestFriendshipCommand(
                requesterId,
                request.recipientUsername()
        );
    }

    public BefriendPlayerCommand toBefriendCommand(UUID friendshipId, UUID recipientId) {
        return new BefriendPlayerCommand(
                friendshipId,
                recipientId
        );
    }

    public DeclineFriendshipCommand toDeclineCommand(UUID friendshipId, UUID recipientId) {
        return new DeclineFriendshipCommand(
                friendshipId,
                recipientId
        );
    }

    public EndFriendshipCommand toEndCommand(UUID friendshipId, UUID initiatedBy) {
        return new EndFriendshipCommand(
                friendshipId,
                initiatedBy
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

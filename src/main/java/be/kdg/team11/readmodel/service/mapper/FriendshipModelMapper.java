package be.kdg.team11.readmodel.service.mapper;

import be.kdg.team11.readmodel.controller.dto.FriendDto;
import be.kdg.team11.readmodel.models.FriendshipModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipModelMapper {

    public FriendDto toFriendDtoAsRequester(UUID recipientId, PlayerModel recipientPlayer) {
        return new FriendDto(
                recipientId,
                recipientPlayer.getUsername(),
                recipientPlayer.getPictureUrl()
        );
    }

    public FriendDto toFriendDtoAsRecipient(UUID requesterId, PlayerModel requesterPlayer) {
        return new FriendDto(
                requesterId,
                requesterPlayer.getUsername(),
                requesterPlayer.getPictureUrl()
        );
    }

    public FriendDto toFriendDto(FriendshipModel friendship, PlayerModel friendPlayer, UUID currentPlayerId) {
        UUID friendId;

        if (friendship.getRequesterId().equals(currentPlayerId)) {
            friendId = friendship.getRecipientId();
        } else {
            friendId = friendship.getRequesterId();
        }

        return new FriendDto(
                friendId,
                friendPlayer.getUsername(),
                friendPlayer.getPictureUrl()
        );
    }
}

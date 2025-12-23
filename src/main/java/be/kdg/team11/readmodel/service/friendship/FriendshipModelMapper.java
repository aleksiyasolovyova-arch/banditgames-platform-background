package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.controller.dto.FriendShipDto;
import be.kdg.team11.readmodel.models.FriendshipModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipModelMapper {
    public FriendShipDto toFriendDto(FriendshipModel friendship, UUID playerId) {

        if (friendship.getRequesterId().equals(playerId)) {
            return new FriendShipDto(
                    friendship.getFriendshipId(),
                    friendship.getState().equals("FRIENDS"),
                    friendship.getRequesterId(),
                    friendship.getRequesterUsername(),
                    friendship.getRequesterPictureUrl()
            );
        } else {
            return new FriendShipDto(
                    friendship.getFriendshipId(),
                    friendship.getState().equals("FRIENDS"),
                    friendship.getRecipientId(),
                    friendship.getRecipientUsername(),
                    friendship.getRecipientPictureUrl()
            );
        }


    }
}

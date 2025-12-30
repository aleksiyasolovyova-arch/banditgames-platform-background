package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.controller.dto.FriendShipModelDto;
import be.kdg.team11.readmodel.models.FriendshipModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipModelMapper {
    public FriendShipModelDto toFriendDto(FriendshipModel friendship, UUID playerId) {

        if (friendship.getRequesterId().equals(playerId)) {
            return new FriendShipModelDto(
                    friendship.getFriendshipId(),
                    friendship.getState().equals("FRIENDS"),
                    friendship.getRecipientId(),
                    friendship.getRecipientUsername(),
                    friendship.getRecipientPictureUrl()
            );
        } else {
            return new FriendShipModelDto(
                    friendship.getFriendshipId(),
                    friendship.getState().equals("FRIENDS"),
                    friendship.getRequesterId(),
                    friendship.getRequesterUsername(),
                    friendship.getRequesterPictureUrl()
            );
        }


    }
}

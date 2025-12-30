package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.controller.dto.FriendShipModelDto;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;

import java.util.List;
import java.util.UUID;

public interface FriendshipModelService {
    void project(BefriendedPlayerEvent event);

    void project(FriendshipEndEvent event);

    void project(FriendshipCreatedEvent event);

    void project(FriendshipDeclinedEvent event);

    List<FriendShipModelDto> getPlayerFriendships(UUID playerId);
}

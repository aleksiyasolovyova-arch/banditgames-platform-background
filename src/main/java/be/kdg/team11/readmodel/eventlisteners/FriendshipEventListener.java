package be.kdg.team11.readmodel.eventlisteners;

import be.kdg.team11.readmodel.service.friendship.FriendshipModelService;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FriendshipEventListener {
    private final FriendshipModelService friendshipModelService;

    public FriendshipEventListener(FriendshipModelService friendshipModelService) {
        this.friendshipModelService = friendshipModelService;
    }

    @EventListener(FriendshipCreatedEvent.class)
    public void friendshipCreated(FriendshipCreatedEvent event) {
        friendshipModelService.project(event);
    }

    @EventListener(BefriendedPlayerEvent.class)
    public void friendshipAccepted(BefriendedPlayerEvent event) {
        friendshipModelService.project(event);
    }

    @EventListener(FriendshipDeclinedEvent.class)
    public void friendshipDeclined(FriendshipDeclinedEvent event) {
        friendshipModelService.project(event);
    }

    @EventListener(FriendshipEndEvent.class)
    public void friendshipEnded(FriendshipEndEvent event) {
        friendshipModelService.project(event);
    }
}

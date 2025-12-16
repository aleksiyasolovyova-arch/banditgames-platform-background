package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FriendshipEventPublisher implements SaveFriendshipPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public FriendshipEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Friendship save(Friendship friendship) {
        friendship.getEventStore().forEach(applicationEventPublisher::publishEvent);
        return friendship;
    }
}

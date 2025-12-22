package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.models.FriendshipModel;
import be.kdg.team11.readmodel.repository.FriendshipModelRepository;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendshipModelServiceImpl implements FriendshipModelService{
    private final FriendshipModelRepository friendshipModelRepository;

    public FriendshipModelServiceImpl(FriendshipModelRepository friendshipModelRepository) {
        this.friendshipModelRepository = friendshipModelRepository;
    }


    @Override
    public void project(BefriendedPlayerEvent event) {
        friendshipModelRepository.findById(event.friendshipId())
                .ifPresent(friendship -> {
                    friendship.setState("FRIENDS");
                    friendship.setAcceptedAt(event.eventPit());
                    friendshipModelRepository.save(friendship);
                });
    }

    //TODO this should be fine when scheduler for deleting is implemented in the write model
    //if its not implemented it will cause inconsistencies and should be changed
    @Override
    public void project(FriendshipEndEvent event) {
        friendshipModelRepository.deleteById(event.friendshipId());
    }

    @Override
    public void project(FriendshipCreatedEvent event) {
        FriendshipModel friendship = new FriendshipModel();
        friendship.setFriendshipId(event.friendshipId());
        friendship.setRequesterId(event.requesterId());
        friendship.setRecipientId(event.recipientId());
        //friendship.setRequesterUsername(event.requesterUsername());
        //friendship.setRecipientUsername(event.recipientUsername());
        //friendship.setRequesterPictureUrl(event.requesterPictureUrl());
        //friendship.setRecipientPictureUrl(event.recipientPictureUrl());
        friendship.setState("REQUESTED");
        friendship.setCreatedAt(event.eventPit());

        friendshipModelRepository.save(friendship);

    }

    @Override
    public void project(FriendshipDeclinedEvent event) {
        friendshipModelRepository.findById(event.friendshipId())
                .ifPresent(friendship -> {
                    friendship.setState("DECLINED");
                    friendshipModelRepository.save(friendship);
                });
    }

}

package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.controller.dto.FriendShipModelDto;
import be.kdg.team11.readmodel.models.FriendshipModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.FriendshipModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FriendshipModelServiceImpl implements FriendshipModelService {
    private final FriendshipModelRepository friendshipModelRepository;
    private final PlayerModelRepository playerModelRepository;
    private final FriendshipModelMapper friendshipModelMapper;

    public FriendshipModelServiceImpl(FriendshipModelRepository friendshipModelRepository,
                                      PlayerModelRepository playerModelRepository,
                                      FriendshipModelMapper friendshipModelMapper) {
        this.friendshipModelRepository = friendshipModelRepository;
        this.playerModelRepository = playerModelRepository;
        this.friendshipModelMapper = friendshipModelMapper;
    }

    @Override
    public void project(FriendshipCreatedEvent event) {
        FriendshipModel friendship = new FriendshipModel();


        PlayerModel requester = playerModelRepository.findById(event.requesterId()).get();
        PlayerModel recipient = playerModelRepository.findById(event.recipientId()).get();

        friendship.setFriendshipId(event.friendshipId());
        friendship.setRequesterId(requester.getPlayerId());
        friendship.setRecipientId(recipient.getPlayerId());
        friendship.setRequesterUsername(requester.getUsername());
        friendship.setRecipientUsername(recipient.getUsername());
        friendship.setRequesterPictureUrl(requester.getPictureUrl());
        friendship.setRecipientPictureUrl(recipient.getPictureUrl());

        friendship.setState("REQUESTED");
        friendship.setCreatedAt(event.eventPit());

        friendshipModelRepository.save(friendship);

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

    @Override
    public void project(FriendshipEndEvent event) {
        friendshipModelRepository.deleteById(event.friendshipId());
    }

    @Override
    public void project(FriendshipDeclinedEvent event) {
        friendshipModelRepository.deleteById(event.friendshipId());
    }

    @Override
    public List<FriendShipModelDto> getPlayerFriendships(UUID playerId) {
        return friendshipModelRepository.findFriendshipsByPlayerIdWhereStateFriendsOrRequested(playerId).stream()
                .map(friendship -> friendshipModelMapper.toFriendDto(friendship, playerId))
                .sorted(Comparator.comparing(FriendShipModelDto::username))
                .toList();
    }
}

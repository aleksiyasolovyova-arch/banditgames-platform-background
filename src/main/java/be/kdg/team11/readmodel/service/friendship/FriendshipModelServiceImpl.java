package be.kdg.team11.readmodel.service.friendship;

import be.kdg.team11.readmodel.controller.dto.FriendDto;
import be.kdg.team11.readmodel.models.FriendshipModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.FriendshipModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.readmodel.service.mapper.FriendshipMapper;
import be.kdg.team11.sharedkernel.events.friendship.BefriendedPlayerEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipCreatedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipDeclinedEvent;
import be.kdg.team11.sharedkernel.events.friendship.FriendshipEndEvent;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendshipModelServiceImpl implements FriendshipModelService{
    private final FriendshipModelRepository friendshipModelRepository;
    private final PlayerModelRepository playerModelRepository;
    private final FriendshipMapper friendshipMapper;

    public FriendshipModelServiceImpl(FriendshipModelRepository friendshipModelRepository,
                                      PlayerModelRepository playerModelRepository,
                                      FriendshipMapper friendshipMapper) {
        this.friendshipModelRepository = friendshipModelRepository;
        this.playerModelRepository = playerModelRepository;
        this.friendshipMapper = friendshipMapper;
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

    @Override
    public List<FriendDto> getPlayerFriends(UUID playerId) {
        List<FriendshipModel> allFriendships =
                friendshipModelRepository.findFriendshipsByPlayerAndState(playerId, "FRIENDS");

        return allFriendships.stream()
                .map(friendship -> {
                    UUID friendId;
                    if (friendship.getRequesterId().equals(playerId)) {
                        friendId = friendship.getRecipientId();
                    } else {
                        friendId = friendship.getRequesterId();
                    }
                    PlayerModel friendPlayer = playerModelRepository.findById(friendId)
                            .orElse(null);

                    if (friendPlayer != null) {
                        return friendshipMapper.toFriendDto(friendship, friendPlayer, playerId);
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .sorted(Comparator.comparing(FriendDto::username))
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendDto> getIncomingFriendRequests(UUID playerId) {
        List<FriendshipModel> incomingRequests =
                friendshipModelRepository.findByRecipientIdAndState(playerId, "REQUESTED");

        return incomingRequests.stream()
                .map(friendship -> {
                    UUID requesterId = friendship.getRequesterId();

                    PlayerModel requesterPlayer = playerModelRepository.findById(requesterId)
                            .orElse(null);

                    if (requesterPlayer != null) {
                        return new FriendDto(
                                requesterId,
                                requesterPlayer.getUsername(),
                                requesterPlayer.getPictureUrl()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(FriendDto::username))
                .collect(Collectors.toList());
    }
}

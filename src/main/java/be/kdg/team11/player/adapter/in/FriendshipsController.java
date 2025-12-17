package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.FriendshipMapper;
import be.kdg.team11.player.adapter.in.request.RequestFriendshipRequest;
import be.kdg.team11.player.adapter.in.response.FriendshipDto;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.RequestFriendshipCommand;
import be.kdg.team11.player.port.in.RequestFriendshipPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/friendships")
public class FriendshipsController {
    private final RequestFriendshipPort requestFriendshipPort;
    private final FriendshipMapper friendshipMapper;

    public FriendshipsController(
            RequestFriendshipPort requestFriendshipPort,
            FriendshipMapper friendshipMapper
    ) {
        this.requestFriendshipPort = requestFriendshipPort;
        this.friendshipMapper = friendshipMapper;
    }


    /**
     * Initiates a friendship request from the authenticated user to another player.
     * FULL PATH: POST /friendships

     * REQUEST BODY (RequestFriendshipRequest):
     * - recipientId (UUID, required): ID of the player to send the friendship request to

     * RESPONSE BODY (FriendshipDto):
     * - friendshipId (UUID): Unique friendship identifier
     * - requesterId (UUID): ID of the player who initiated the request
     * - recipientId (UUID): ID of the player receiving the request
     * - state (String): Current friendship state ("REQUESTED", "FRIENDS", "DECLINED", "ENDED")

     * HTTP Status Codes:
     * - 201 Created: Friendship request successfully created
     * - 400 Bad Request: Validation failed (invalid/missing fields, or self-request)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping
    public ResponseEntity<FriendshipDto> requestFriendship(
            @Valid @RequestBody RequestFriendshipRequest request
    ) {
        //TODO replace with value from JWT
        UUID requesterId = UUID.randomUUID();

        RequestFriendshipCommand command = friendshipMapper.toCommand(requesterId, request);

        Friendship createdFriendship = requestFriendshipPort.requestFriendship(command);

        FriendshipDto response = friendshipMapper.toResponse(createdFriendship);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

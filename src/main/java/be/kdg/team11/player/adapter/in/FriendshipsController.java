package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.FriendshipMapper;
import be.kdg.team11.player.adapter.in.request.RequestFriendshipRequest;
import be.kdg.team11.player.adapter.in.response.FriendshipDto;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.port.in.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/friendships")
public class FriendshipsController {
    private final RequestFriendshipPort requestFriendshipPort;
    private final BefriendPlayerPort befriendPlayerPort;
    private final DeclineFriendshipPort declineFriendshipPort;
    private final EndFriendshipPort endFriendshipPort;
    private final FriendshipMapper friendshipMapper;

    /**
     * RESPONSE BODY (FriendshipDto):
     * - friendshipId (UUID): Unique friendship identifier
     * - requesterId (UUID): ID of the player who initiated the request
     * - recipientId (UUID): ID of the player receiving the request
     * - state (String): Current friendship state (e.g., "REQUESTED", "FRIENDS", "DECLINED", "ENDED")
     */


    public FriendshipsController(
            RequestFriendshipPort requestFriendshipPort,
            BefriendPlayerPort befriendPlayerPort,
            DeclineFriendshipPort declineFriendshipPort,
            EndFriendshipPort endFriendshipPort,
            FriendshipMapper friendshipMapper
    ) {
        this.requestFriendshipPort = requestFriendshipPort;
        this.befriendPlayerPort = befriendPlayerPort;
        this.declineFriendshipPort = declineFriendshipPort;
        this.endFriendshipPort = endFriendshipPort;
        this.friendshipMapper = friendshipMapper;
    }


    /**
     * Initiates a friendship request from the authenticated user to another player.
     * FULL PATH: POST /friendships
     * <p>
     * REQUEST BODY (RequestFriendshipRequest):
     * - recipientUsername (String, required): Username of the player to send the friendship request to (must be non-null and non-empty)
     * <p>
     * RESPONSE BODY (FriendshipDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Friendship request successfully created
     * - 400 Bad Request: Validation failed (e.g., invalid or missing fields, or self-request)
     * - 500 Internal Server Error: Unexpected server error during friendship request creation
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipDto> requestFriendship(
            @Valid @RequestBody RequestFriendshipRequest request,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID requesterId = UUID.fromString(token.getSubject());

        RequestFriendshipCommand command = friendshipMapper.toRequestCommand(requesterId, request);
        Friendship createdFriendship = requestFriendshipPort.requestFriendship(command);
        FriendshipDto response = friendshipMapper.toResponse(createdFriendship);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Accepts a friendship request, transitioning it from REQUESTED to FRIENDS state.
     * FULL PATH: PUT /friendships/{friendshipId}/befriend
     * <p>
     * PATH PARAMETER:
     * - friendshipId (UUID): ID of the friendship request to accept
     * <p>
     * RESPONSE BODY (FriendshipDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Friendship successfully accepted and transitioned to FRIENDS state
     * - 400 Bad Request: Invalid request or business rule violation (e.g., friendship is not in REQUESTED state)
     * - 404 Not Found: Friendship with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during friendship acceptance
     */
    @PutMapping("/{friendshipId}/befriend")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipDto> acceptFriendship(
            @NotNull @PathVariable UUID friendshipId,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID recipientId = UUID.fromString(token.getSubject());

        BefriendPlayerCommand command = friendshipMapper.toBefriendCommand(friendshipId, recipientId);
        Friendship acceptedFriendship = befriendPlayerPort.befriendPlayer(command);
        FriendshipDto response = friendshipMapper.toResponse(acceptedFriendship);
        return ResponseEntity.ok(response);
    }

    /**
     * Declines a friendship request, transitioning it from REQUESTED to DECLINED state.
     * FULL PATH: PUT /friendships/{friendshipId}/decline
     * PATH PARAMETER:
     * - friendshipId (UUID): ID of the friendship to decline
     * RESPONSE BODY (FriendshipDto):
     * - friendshipId (UUID): Unique friendship identifier
     * - requesterId (UUID): ID of the player who initiated the request
     * - recipientUsername (UUID): ID of the player who declined
     * - state (String): Current friendship state ("DECLINED")
     * HTTP Status Codes:
     * - 200 OK: Friendship successfully declined
     * - 400 Bad Request: Invalid request or business rule violation
     * - 404 Not Found: Friendship with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PutMapping("/{friendshipId}/decline")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipDto> declineFriendship(
            @NotNull @PathVariable UUID friendshipId,
            @AuthenticationPrincipal Jwt token
    ) {

        UUID recipientId = UUID.fromString(token.getSubject());


        DeclineFriendshipCommand command = friendshipMapper.toDeclineCommand(friendshipId, recipientId);
        Friendship declinedFriendship = declineFriendshipPort.declineFriendship(command);
        FriendshipDto response = friendshipMapper.toResponse(declinedFriendship);
        return ResponseEntity.ok(response);
    }

    /**
     * Declines a friendship request, transitioning it from REQUESTED to DECLINED state.
     * FULL PATH: PUT /friendships/{friendshipId}/decline
     * <p>
     * PATH PARAMETER:
     * - friendshipId (UUID): ID of the friendship request to decline
     * <p>
     * RESPONSE BODY (FriendshipDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Friendship successfully declined and transitioned to DECLINED state
     * - 400 Bad Request: Invalid request or business rule violation (e.g., friendship is not in REQUESTED state)
     * - 404 Not Found: Friendship with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during friendship decline
     */
    @PostMapping("/{friendshipId}/end")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipDto> endFriendship(
            @NotNull @PathVariable UUID friendshipId,
            @AuthenticationPrincipal Jwt token
    ) {

        UUID initiatedBy = UUID.fromString(token.getSubject());

        EndFriendshipCommand command = friendshipMapper.toEndCommand(friendshipId, initiatedBy);
        Friendship endedFriendship = endFriendshipPort.endFriendship(command);
        FriendshipDto response = friendshipMapper.toResponse(endedFriendship);
        return ResponseEntity.ok(response);
    }

}

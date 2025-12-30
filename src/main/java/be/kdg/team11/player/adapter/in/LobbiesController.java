package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.LobbyMapper;
import be.kdg.team11.player.adapter.in.request.CreateLobbyForAIRequest;
import be.kdg.team11.player.adapter.in.request.CreateLobbyForFriendRequest;
import be.kdg.team11.player.adapter.in.request.CreateLobbyForStrangerRequest;
import be.kdg.team11.player.adapter.in.response.LobbyDto;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/lobbies")
public class LobbiesController {
    private final LobbyMapper lobbyMapper;
    private final CreateLobbyForStrangerPort createLobbyForStrangerPort;
    private final CreateLobbyForAIPort createLobbyForAIPort;
    private final CreateLobbyForFriendPort createLobbyForFriendPort;

    /**
     * RESPONSE BODY (LobbyDto):
     * - lobbyId (UUID): Unique identifier for the created lobby
     * - gameReference (UUID): ID of the game associated with this lobby
     * - player1Id (UUID): ID of the player who created the lobby (the authenticated user)
     * - player2Id (UUID): ID of the invited stranger player
     * - startTime (LocalDateTime): Timestamp when the game session started (null if not started)
     * - endTime (LocalDateTime): Timestamp when the game session ended (null if ongoing)
     * - lobbyResult (String): Result of the game session (e.g., "PLAYER1_WON", "PLAYER2_WON", "DRAW")
     * - link (String): Shareable link or reference for the lobby
     */


    public LobbiesController(LobbyMapper lobbyMapper, CreateLobbyForStrangerPort createLobbyForStrangerPort, CreateLobbyForAIPort createLobbyForAIPort, CreateLobbyForFriendPort createLobbyForFriendPort) {
        this.lobbyMapper = lobbyMapper;
        this.createLobbyForStrangerPort = createLobbyForStrangerPort;
        this.createLobbyForAIPort = createLobbyForAIPort;
        this.createLobbyForFriendPort = createLobbyForFriendPort;
    }

    /**
     * Creates a lobby for a game with a stranger (player not in user's friend list).
     * Endpoint: POST /lobbies/stranger
     * <p>
     * REQUEST BODY (CreateLobbyForStrangerRequest):
     * - gameId (UUID, required): ID of the game to create the lobby for (must be non-null) [file:9]
     * - strangerUserName (String, required): Username of the stranger player to invite to the lobby (must be non-blank) [file:9]
     * <p>
     * RESPONSE BODY (LobbyDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Lobby successfully created for stranger
     * - 400 Bad Request: Validation failed (e.g., invalid or missing fields, game ID or username invalid)
     * - 500 Internal Server Error: Unexpected server error during lobby creation
     */
    @PostMapping("/stranger")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> createLobbyForStranger(
            @Valid @RequestBody CreateLobbyForStrangerRequest request,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getSubject());
        CreateLobbyForStrangerCommand command = new CreateLobbyForStrangerCommand(
                PlayerId.of(playerId),
                request.gameId(),
                request.strangerUserName()
        );
        Lobby lobby = createLobbyForStrangerPort.create(command);
        LobbyDto response = lobbyMapper.toDto(lobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Creates a lobby for a game with an AI opponent.
     * Endpoint: POST /lobbies/ai
     * <p>
     * REQUEST BODY (CreateLobbyForAIRequest):
     * - gameId (UUID, required): ID of the game to create the lobby for (must be non-null)
     * <p>
     * RESPONSE BODY (LobbyDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Lobby successfully created for AI opponent
     * - 400 Bad Request: Validation failed (e.g., invalid or missing game ID, or game does not support AI)
     * - 500 Internal Server Error: Unexpected server error during lobby creation
     */
    @PostMapping("/ai")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> createLobbyForAI(
            @Valid @RequestBody CreateLobbyForAIRequest request,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getSubject());
        CreateLobbyForAICommand command = new CreateLobbyForAICommand(
                PlayerId.of(playerId),
                request.gameId()
        );
        Lobby lobby = createLobbyForAIPort.create(command);
        LobbyDto response = lobbyMapper.toDto(lobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Creates a lobby for a game with a friend from the user's friend list.
     * Endpoint: POST /lobbies/friend
     * <p>
     * REQUEST BODY (CreateLobbyForFriendRequest):
     * - gameId (UUID, required): ID of the game to create the lobby for (must be non-null)
     * - friendId (UUID, required): ID of the friend player to invite to the lobby (must be non-null and must be in the authenticated user's friend list)
     * <p>
     * RESPONSE BODY (LobbyDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Lobby successfully created for friend
     * - 400 Bad Request: Validation failed (e.g., invalid or missing fields, specified friend is not in user's friend list, or game ID is invalid)
     * - 500 Internal Server Error: Unexpected server error during lobby creation
     */
    @PostMapping("/friend")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> createLobbyForFriend(
            @Valid @RequestBody CreateLobbyForFriendRequest request,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getSubject());
        CreateLobbyForFriendCommand command = new CreateLobbyForFriendCommand(
                PlayerId.of(playerId),
                request.gameId(),
                PlayerId.of(request.friendId())
        );
        Lobby lobby = createLobbyForFriendPort.create(command);
        LobbyDto response = lobbyMapper.toDto(lobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

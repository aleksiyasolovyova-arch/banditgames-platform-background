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

    public LobbiesController(LobbyMapper lobbyMapper, CreateLobbyForStrangerPort createLobbyForStrangerPort, CreateLobbyForAIPort createLobbyForAIPort, CreateLobbyForFriendPort createLobbyForFriendPort) {
        this.lobbyMapper = lobbyMapper;
        this.createLobbyForStrangerPort = createLobbyForStrangerPort;
        this.createLobbyForAIPort = createLobbyForAIPort;
        this.createLobbyForFriendPort = createLobbyForFriendPort;
    }

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

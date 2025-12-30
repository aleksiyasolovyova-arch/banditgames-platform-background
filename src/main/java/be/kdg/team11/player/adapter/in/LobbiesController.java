package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.LobbyMapper;
import be.kdg.team11.player.adapter.in.request.CreateLobbyForStrangerRequest;
import be.kdg.team11.player.adapter.in.request.RequestFriendshipRequest;
import be.kdg.team11.player.adapter.in.response.LobbyDto;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.CreateLobbyForStrangerCommand;
import be.kdg.team11.player.port.in.CreateLobbyForStrangerPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lobbies")
public class LobbiesController {
    private final LobbyMapper lobbyMapper;
    private final CreateLobbyForStrangerPort createLobbyForStrangerPort;

    public LobbiesController(LobbyMapper lobbyMapper, CreateLobbyForStrangerPort createLobbyForStrangerPort) {
        this.lobbyMapper = lobbyMapper;
        this.createLobbyForStrangerPort = createLobbyForStrangerPort;
    }

    @PostMapping("/stranger")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> createLobbyForStranger(
            @Valid @RequestBody CreateLobbyForStrangerRequest request,
            @AuthenticationPrincipal Jwt token
    ){
        UUID playerId = UUID.fromString(token.getSubject());
        CreateLobbyForStrangerCommand command = new CreateLobbyForStrangerCommand(
                PlayerId.of(playerId),
                request.gameId(),
                request.strangerUserName()
        );
        Lobby lobby = createLobbyForStrangerPort.createLobbyForStrangers(command);
        LobbyDto response = lobbyMapper.toDto(lobby);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

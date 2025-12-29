package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.LobbyMapper;
import be.kdg.team11.player.adapter.in.response.LobbyDto;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.port.in.AcceptLobbyCommand;
import be.kdg.team11.player.port.in.AcceptLobbyPort;
import be.kdg.team11.player.port.in.RejectLobbyCommand;
import be.kdg.team11.player.port.in.RejectLobbyPort;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/lobbies")
public class LobbiesController {
    private final AcceptLobbyPort acceptLobbyPort;
    private final RejectLobbyPort rejectLobbyPort;
    private final LobbyMapper lobbyMapper;

    public LobbiesController(
            AcceptLobbyPort acceptLobbyPort,
            RejectLobbyPort rejectLobbyPort,
            LobbyMapper lobbyMapper) {
        this.acceptLobbyPort = acceptLobbyPort;
        this.rejectLobbyPort = rejectLobbyPort;
        this.lobbyMapper = lobbyMapper;
    }

    @PostMapping("/{lobbyId}/accept")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> acceptLobby(
            @NotNull @PathVariable UUID lobbyId,
            @AuthenticationPrincipal Jwt token) {

        UUID playerId = UUID.fromString(token.getSubject());
        AcceptLobbyCommand command = new AcceptLobbyCommand(lobbyId, playerId);

        Lobby updatedLobby = acceptLobbyPort.accept(command);
        LobbyDto response = lobbyMapper.toDto(updatedLobby);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{lobbyId}/reject")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LobbyDto> rejectLobby(
            @NotNull @PathVariable UUID lobbyId,
            @AuthenticationPrincipal Jwt token) {

        UUID playerId = UUID.fromString(token.getSubject());
        RejectLobbyCommand command = new RejectLobbyCommand(lobbyId, playerId);

        Lobby updatedLobby = rejectLobbyPort.reject(command);
        LobbyDto response = lobbyMapper.toDto(updatedLobby);

        return ResponseEntity.ok(response);
    }
}

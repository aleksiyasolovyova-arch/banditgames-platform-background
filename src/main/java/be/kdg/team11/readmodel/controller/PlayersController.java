package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.FriendShipModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerProfileDto;
import be.kdg.team11.readmodel.service.player.PlayerModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayersController {
    private final PlayerModelService playerModelService;

    public PlayersController(PlayerModelService playerModelService) {
        this.playerModelService = playerModelService;
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerProfileDto> getPlayerProfile(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        PlayerProfileDto profile = playerModelService.getPlayerProfile(playerId);
        return ResponseEntity.ok(profile);
    }
}

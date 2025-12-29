package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.player.PlayerNavBarDto;
import be.kdg.team11.readmodel.controller.dto.player.PlayerOpponentDto;
import be.kdg.team11.readmodel.controller.dto.player.PlayerProfileDto;
import be.kdg.team11.readmodel.service.player.PlayerModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayersController {
    private final PlayerModelService playerModelService;

    public PlayersController(PlayerModelService playerModelService) {
        this.playerModelService = playerModelService;
    }

    //default exception handling for now. could be changed with custom ones
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerProfileDto> getPlayerProfile(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        try {
            PlayerProfileDto profile = playerModelService.getPlayerProfile(playerId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/opponent/{opponentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerOpponentDto> getOpponent(@PathVariable UUID opponentId) {
        try {
            PlayerOpponentDto opponent = playerModelService.getOpponent(opponentId);
            return ResponseEntity.ok(opponent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/navbar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerNavBarDto> getPlayerNavBar(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        try {
            PlayerNavBarDto navBar = playerModelService.getPlayerNavBar(playerId);
            return ResponseEntity.ok(navBar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

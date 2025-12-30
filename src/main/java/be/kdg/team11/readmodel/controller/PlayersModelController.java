package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.player.PlayerModelDto;
import be.kdg.team11.readmodel.service.player.PlayerModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayersModelController {
    private final PlayerModelService playerModelService;

    public PlayersModelController(PlayerModelService playerModelService) {
        this.playerModelService = playerModelService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<? extends PlayerModelDto> getPlayer(
            @AuthenticationPrincipal Jwt token,
            @RequestParam GetPlayerScope scope){
        UUID playerId = UUID.fromString(token.getSubject());
        try {
            PlayerModelDto dto = switch (scope) {
                case NAVBAR -> playerModelService.getPlayerNavBar(playerId);
                case PROFILE -> playerModelService.getPlayerProfile(playerId);
            };
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public enum GetPlayerScope{
        NAVBAR,
        PROFILE,
    }
}

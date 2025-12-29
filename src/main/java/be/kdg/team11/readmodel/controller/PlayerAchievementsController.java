package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.PlayerAchievementsDto;
import be.kdg.team11.readmodel.service.playerachievements.PlayerAchievementsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("player-achievements")
public class PlayerAchievementsController {
    private final PlayerAchievementsService playerAchievementsService;

    public PlayerAchievementsController(PlayerAchievementsService playerAchievementsService) {
        this.playerAchievementsService = playerAchievementsService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerAchievementsDto> getPlayerAchievements(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        return ResponseEntity.ok(playerAchievementsService.getPlayerAchievements(playerId));
    }
}

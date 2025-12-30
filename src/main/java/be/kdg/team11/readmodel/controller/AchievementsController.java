package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.AchievementsDto;
import be.kdg.team11.readmodel.service.achievements.AchievementsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("achievements")
public class AchievementsController {
    private final AchievementsService achievementsService;

    public AchievementsController(AchievementsService achievementsService) {
        this.achievementsService = achievementsService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AchievementsDto> getAchievements(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        return ResponseEntity.ok(achievementsService.getAchievements(playerId));
    }
}

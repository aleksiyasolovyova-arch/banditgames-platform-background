package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.response.PlayerGamesDto;
import be.kdg.team11.readmodel.service.PlayerGamesService;
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
@RequestMapping("player/games")
public class PlayerGamesController {
    private final PlayerGamesService playerGamesService;

    public PlayerGamesController(PlayerGamesService playerGamesService) {
        this.playerGamesService = playerGamesService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PlayerGamesDto>> getPlayerGames(@AuthenticationPrincipal Jwt token){
        return ResponseEntity.ok(playerGamesService.getAllForPlayerId(UUID.fromString(token.getSubject())));
    }
}

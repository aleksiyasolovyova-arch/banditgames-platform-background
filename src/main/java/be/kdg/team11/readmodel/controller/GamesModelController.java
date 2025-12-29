package be.kdg.team11.readmodel.controller;

import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.readmodel.service.game.GameModelMapper;
import be.kdg.team11.readmodel.controller.dto.game.GameModelDto;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.service.game.GameModelService;
import be.kdg.team11.readmodel.service.player.PlayerModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("games")
public class GamesModelController {
    private final GameModelService gameModelService;

    public GamesModelController(GameModelService gameModelService) {
        this.gameModelService = gameModelService;
    }

    /**
     * Retrieves all games in the system with different structures depending on the authentication and role.
     * FULL PATH: /games (GET)
     * RESPONSE BODY (List<GameDto>):
     * - gameId (UUID): Unique game identifier
     * - name (String): Game name
     * - description (String): Game description
     * - pictureUrl (String): URL to game icon/screenshot
     * - gameUrl (String): URL to playable game
     * - gameCreatorName (String): Name of the game creator
     * - registrationState (String): Game review state (PENDING, PASSED, FAILED)
     * - rules (List<RuleDto>): List of game rules
     *   - description (String): Rule description
     * - gameAchievements (List<GameAchievementDto>): List of game-specific gameAchievements
     *   - code (String): Achievement code
     *   - description (String): Achievement description
     * - playableWithAI (boolean): Whether the game can be played with an AI
     * - isFavourite (boolean): If a player has this game as their favourite
     * - isPending (boolean): If the game passed the review or is still awaiting review from the admin
     * HTTP Status Codes:
     * - 200 OK: Games retrieved successfully
     * - 500 Internal Server Error: Unexpected server error
     */

    @GetMapping
    public ResponseEntity<List<? extends GameModelDto>> getGames(@AuthenticationPrincipal Jwt token){
        List<? extends GameModelDto> gameDtoList;
        if (token != null){
            if (token.getClaimAsStringList("authorities") != null && token.getClaimAsStringList("authorities").contains("ROLE_ADMIN")){
                gameDtoList = gameModelService.getAllForAdmin();
            } else {
                UUID playerId = UUID.fromString(token.getSubject());
                gameDtoList = gameModelService.getAllForPlayer(playerId);
            }
        } else {
            gameDtoList = gameModelService.getAllForPublic();
        }
        return ResponseEntity.ok(gameDtoList);
    }
}

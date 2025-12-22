package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.mapper.GameRMMapper;
import be.kdg.team11.readmodel.controller.dto.GameDto;
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
    private final PlayerModelService playerModelService;
    private final GameRMMapper gameRMMapper;

    public GamesModelController(GameModelService gameModelService,
                                PlayerModelService playerModelService,
                                GameRMMapper gameRMMapper) {
        this.gameModelService = gameModelService;
        this.playerModelService = playerModelService;
        this.gameRMMapper = gameRMMapper;
    }

    /**
     * Retrieves all games in the system.
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
     * - achievements (List<GameAchievementDto>): List of game-specific achievements
     *   - code (String): Achievement code
     *   - description (String): Achievement description
     * - playableWithAI (boolean): Whether the game can be played with an AI
     * HTTP Status Codes:
     * - 200 OK: Games retrieved successfully
     * - 500 Internal Server Error: Unexpected server error
     */

    //TODO move mapping logic to the service, keep authenthication and security logic here.

    @GetMapping
    public ResponseEntity<List<? extends GameDto>> getGames(@AuthenticationPrincipal Jwt token){
        List<? extends GameDto> gameDtoList;
        if (token != null){
            if (token.getClaimAsStringList("authorities") != null && token.getClaimAsStringList("authorities").contains("ROLE_ADMIN")){
                gameDtoList = gameModelService.getAllWithRulesAndAchievements().stream().map(
                        gameRMMapper::toAdminGameDto
                ).toList();
            } else {
                UUID playerId = UUID.fromString(token.getSubject());
                UUID favouriteGameId = playerModelService.findByPlayerId(playerId)
                        .map(PlayerModel::getFavouriteGameId)
                        .orElse(null);
                gameDtoList = gameModelService.getAllWithRules().stream().map(
                        gameRM -> gameRMMapper.toPlayerGamesDto(gameRM,favouriteGameId)
                ).toList();
            }
        } else {
            gameDtoList = gameModelService.getAll().stream().map(
                    gameRMMapper::toPublicGameDto
            ).toList();
        }
        return ResponseEntity.ok(gameDtoList);
    }
}

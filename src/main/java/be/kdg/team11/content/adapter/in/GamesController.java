package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.GameMapper;
import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.request.UpdateGameRequest;
import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final RegisterGamePort registerGamePort;
    private final PassGameReviewPort passGameReviewPort;
    private final FailGameReviewPort failGameReviewPort;
    private final ModifyGameUrlsPort modifyGameUrlsPort;
    private final FindAllGamesQueryPort showAllGamesPort;
    private final TogglePlayableWithAIPort togglePlayableWithAIPort;
    private final GameMapper gameMapper;


    /**
     * REQUEST BODY:
     * - name (String, required): Game name (1-255 chars)
     * - description (String, required): Game description (1-500 chars)
     * - price (BigDecimal, required): Game price (non-negative)
     * - pictureUrl (String, required): URL to game image
     * - gameUrl (String, required): URL to game content
     * - gameCreatorName (String, required): Name of game creator (1-100 chars)
     * - rules (List<RuleRequest>, required): Game rules (at least one required)
     * - description (String, required): Rule description (1-255 chars)
     * - achievements (List<GameAchievementRequest>, required): Linked achievements (at least one required)
     * - code (String, required): Achievement code (1-100 chars)
     * - description (String, required): Achievement description (1-500 chars)
     * - playableWithAI (boolean, required): Whether the game can be played with an AI
     */

    public GamesController(RegisterGamePort registerGamePort,
                           PassGameReviewPort passGameReviewPort,
                           FailGameReviewPort failGameReviewPort,
                           ModifyGameUrlsPort modifyGameUrlsPort,
                           FindAllGamesQueryPort showAllGamesPort,
                           TogglePlayableWithAIPort togglePlayableWithAIPort,
                           GameMapper gameMapper) {
        this.registerGamePort = registerGamePort;
        this.passGameReviewPort = passGameReviewPort;
        this.failGameReviewPort = failGameReviewPort;
        this.modifyGameUrlsPort = modifyGameUrlsPort;
        this.showAllGamesPort = showAllGamesPort;
        this.togglePlayableWithAIPort = togglePlayableWithAIPort;
        this.gameMapper = gameMapper;
    }

    /**
     * Registers a new game in the system.
     * FULL PATH: /games (POST)
     * RESPONSE BODY (GameDto):
     * - gameId (UUID): Unique game identifier
     * - name (String): Game name
     * - description (String): Game description
     * - price (BigDecimal): Game price
     * - pictureUrl (String): Game image URL
     * - gameUrl (String): Game content URL
     * - gameCreatorName (String): Creator name
     * - registrationState (String): Current state (e.g., "PENDING", "ACCEPTED", "REJECTED")
     * - rules (List<RuleDto>): Game rules
     * - achievements (List<GameAchievementDto>): Linked achievements
     * - playableWithAI (boolean): Whether the game can be played with an AI
     * HTTP Status Codes:
     * - 201 Created: Game successfully registered
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping
    public ResponseEntity<GameDto> registerGame(
            @Valid @RequestBody RegisterGameRequest request) {
        Game createdGame = registerGamePort.register(
                gameMapper.toCommand(request)
        );
         GameDto response = gameMapper.toResponse(createdGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates game URLs (picture and game content URLs).
     * FULL PATH: /games/{gameId} (PUT)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to update
     * REQUEST BODY (UpdateGameRequest):
     * - pictureUrl (String, required): New picture URL (cannot be blank)
     * - gameUrl (String, required): New game URL (cannot be blank)
     * HTTP Status Codes:
     * - 200 OK: Game URLs successfully updated
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 404 Not Found: Game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PatchMapping("/{gameId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> updateGame(
            @NotNull @PathVariable UUID gameId,
            @Valid @RequestBody UpdateGameRequest request) {
        Game updatedGame = modifyGameUrlsPort.modifyGameUrls(
                gameMapper.toUpdateCommand(gameId, request)
        );
         GameDto response = gameMapper.toResponse(updatedGame);
        return ResponseEntity.ok(response);
    }

    /**
     * Accepts a game, transitioning it from PENDING to ACCEPTED state.
     * FULL PATH: /games/{gameId}/pass (PUT)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to accept
     * HTTP Status Codes:
     * - 200 OK: Game successfully accepted
     * - 404 Not Found: Game with given ID doesn't exist
     * - 409 Conflict: Invalid state transition (already accepted/rejected)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PatchMapping("/{gameId}/pass")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> passGameReview(
           @NotNull @PathVariable UUID gameId) {
        Game acceptedGame = passGameReviewPort.passGameReview(
                new PassGameReviewCommand(gameId)
        );
         GameDto response = gameMapper.toResponse(acceptedGame);
        return ResponseEntity.ok(response);
    }

    /**
     * Rejects a game, transitioning it from PENDING to REJECTED state.
     * FULL PATH: /games/{gameId}/fail (PUT)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to reject
     * HTTP Status Codes:
     * - 200 OK: Game successfully rejected
     * - 404 Not Found: Game with given ID doesn't exist
     * - 409 Conflict: Invalid state transition (already accepted/rejected)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PatchMapping("/{gameId}/fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> failGameReview(
            @NotNull @PathVariable UUID gameId) {
        Game rejectedGame = failGameReviewPort.failGameReview(
                new FailGameReviewCommand(gameId)
        );
         GameDto response = gameMapper.toResponse(rejectedGame);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("{gameId}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> togglePlayableWithAI(
            @NotNull @PathVariable UUID gameId
    ){
        Game game = togglePlayableWithAIPort.togglePlayableWithAI(new TogglePlayableWithAICommand(gameId));
         GameDto response = gameMapper.toResponse(game);
        return ResponseEntity.ok(response);
    }



}

package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.GameMapper;
import be.kdg.team11.content.adapter.in.request.ModifyGameUrlsRequest;
import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {

    /**
     * RESPONSE BODY (GameDto):
     * - gameId (UUID): Unique game identifier (auto-generated)
     * - name (String): Game name (echoed from request)
     * - description (String): Game description (echoed from request)
     * - pictureUrl (String): Game image URL (echoed from request)
     * - gameUrl (String): Game playable content URL (echoed from request)
     * - gameCreatorName (String): Creator name (echoed from request)
     * - registrationState (String): Current registration state (e.g., "PENDING", "PASSED", "FAILED")
     * - rules (List<RuleDto>): List of game rules with descriptions
     *   - RuleDto.description (String): Rule description
     * - gameAchievements (List<GameAchievementDto>): List of game achievements with codes and descriptions
     *   - GameAchievementDto.code (String): Achievement code
     *   - GameAchievementDto.description (String): Achievement description
     * - playableWithAI (boolean): Whether the game can be played with an AI opponent (echoed from request)
     */

    private final RegisterGamePort registerGamePort;
    private final PassGameReviewPort passGameReviewPort;
    private final FailGameReviewPort failGameReviewPort;
    private final ModifyGameUrlsPort modifyGameUrlsPort;
    private final TogglePlayableWithAIPort togglePlayableWithAIPort;
    private final GameMapper gameMapper;

    public GamesController(RegisterGamePort registerGamePort,
                           PassGameReviewPort passGameReviewPort,
                           FailGameReviewPort failGameReviewPort,
                           ModifyGameUrlsPort modifyGameUrlsPort,
                           TogglePlayableWithAIPort togglePlayableWithAIPort,
                           GameMapper gameMapper) {
        this.registerGamePort = registerGamePort;
        this.passGameReviewPort = passGameReviewPort;
        this.failGameReviewPort = failGameReviewPort;
        this.modifyGameUrlsPort = modifyGameUrlsPort;
        this.togglePlayableWithAIPort = togglePlayableWithAIPort;
        this.gameMapper = gameMapper;
    }

    /**
     * Registers a new game in the system.
     * Endpoint: POST /games
     * Required Role: ADMIN
     * <p>
     * REQUEST BODY (RegisterGameRequest):
     * - name (String, required): Game name (1-255 characters, not blank)
     * - description (String, required): Game description (1-500 characters, not blank)
     * - pictureUrl (String, required): Game image URL (valid URL format, not blank)
     * - gameUrl (String, required): Game playable content URL (valid URL format, not blank)
     * - gameCreatorName (String, required): Creator name (1-100 characters, not blank)
     * - rules (List<RuleRequest>, required): List of game rules with at least one rule
     *   - RuleRequest.description (String): Rule description (1-255 characters, not blank)
     * - gameAchievements (List<GameAchievementRequest>, required): List of achievements with at least one achievement
     *   - GameAchievementRequest.code (String): Achievement code (1-100 characters, not blank)
     *   - GameAchievementRequest.description (String): Achievement description (1-500 characters, not blank)
     * - playableWithAI (boolean, required): Whether the game can be played with an AI opponent
     * <p>
     * RESPONSE BODY (GameDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Game successfully registered with generated ID
     * - 400 Bad Request: Validation failed (e.g., missing rules/achievements, invalid URLs, invalid field lengths)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error during game registration
     */
    @PostMapping
    public ResponseEntity<GameDto> registerGame(
            @Valid @RequestBody RegisterGameRequest request) {
        Game createdGame = registerGamePort.register(
                gameMapper.toRegisterGameCommand(request)
        );
        GameDto response = gameMapper.toResponse(createdGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates the picture and playable content URLs of an existing game.
     * Endpoint: PATCH /games/{gameId}/urls
     * Required Role: ADMIN
     * <p>
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to update
     * <p>
     * REQUEST BODY (ModifyGameUrlsRequest):
     * - pictureUrl (String, required): New game picture/thumbnail URL (must be a valid URL format, not blank)
     * - gameUrl (String, required): New game playable content URL (must be a valid URL format, not blank)
     * <p>
     * RESPONSE BODY (GameDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Game URLs successfully updated
     * - 400 Bad Request: Validation failed (e.g., invalid or missing fields)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during update
     */
    @PatchMapping("/{gameId}/urls")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDto> modifyGameUrls(
            @NotNull @PathVariable UUID gameId,
            @Valid @RequestBody ModifyGameUrlsRequest request) {
        Game updatedGame = modifyGameUrlsPort.modify(
                gameMapper.toModifyGameUrlsCommand(gameId, request)
        );
        GameDto response = gameMapper.toResponse(updatedGame);
        return ResponseEntity.ok(response);
    }

    /**
     * Accepts a game, transitioning it from PENDING to PASSED state.
     * Endpoint: PATCH /games/{gameId}/pass
     * Required Role: ADMIN
     * <p>
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to accept and transition to PASSED state
     * <p>
     * RESPONSE BODY (GameDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Game successfully accepted and transitioned to PASSED state
     * - 400 Bad Request: Invalid state transition (e.g., game is not in PENDING state, already PASSED or FAILED)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during acceptance
     */
    @PatchMapping("/{gameId}/pass")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDto> passGameReview(
            @NotNull @PathVariable UUID gameId) {
        Game acceptedGame = passGameReviewPort.pass(
                new PassGameReviewCommand(gameId)
        );
        GameDto response = gameMapper.toResponse(acceptedGame);
        return ResponseEntity.ok(response);
    }

    /**
     * Rejects a game, transitioning it from PENDING to FAILED state.
     * Endpoint: PATCH /games/{gameId}/fail
     * Required Role: ADMIN
     * <p>
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to reject and transition to FAILED state
     * <p>
     * RESPONSE BODY (GameDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Game successfully failed
     * - 400 Bad Request: Invalid state transition (already passed/failed)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PatchMapping("/{gameId}/fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDto> failGameReview(
            @NotNull @PathVariable UUID gameId) {
        Game rejectedGame = failGameReviewPort.failGameReview(
                new FailGameReviewCommand(gameId)
        );
        GameDto response = gameMapper.toResponse(rejectedGame);
        return ResponseEntity.ok(response);
    }


    /**
     * Toggles the AI playability flag of a game between enabled and disabled states.
     * Endpoint: PATCH /games/{gameId}/toggle-playable-ai
     * Required Role: ADMIN
     * <p>
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game whose AI playability flag will be toggled
     * <p>
     * RESPONSE BODY (GameDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: AI playability flag successfully toggled
     * - 404 Not Found: Game with the given ID does not exist
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error during toggle operation
     */
    @PatchMapping("{gameId}/toggle-playable-ai")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDto> togglePlayableWithAI(
            @NotNull @PathVariable UUID gameId
    ) {
        Game game = togglePlayableWithAIPort.toggle(new TogglePlayableWithAICommand(gameId));
        GameDto response = gameMapper.toResponse(game);
        return ResponseEntity.ok(response);
    }


}

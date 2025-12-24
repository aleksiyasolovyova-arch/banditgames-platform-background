package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.GameMapper;
import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.request.ModifyGameUrlsRequest;
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
     *
     * REQUEST BODY (RegisterGameRequest):
     * - name (String, required): Game name (1-255 characters, cannot be blank)
     * - description (String, required): Game description (5-500 characters, cannot be blank)
     * - pictureUrl (String, required): Game image URL (must be valid URL format, cannot be blank)
     * - gameUrl (String, required): Game playable content URL (must be valid URL format, cannot be blank)
     * - gameCreatorName (String, required): Creator name (1-100 characters, cannot be blank)
     * - rules (List<RuleRequest>, required): At least one game rule must be provided
     *   - RuleRequest.description (String): Rule description (1-255 characters, cannot be blank)
     * - achievements (List<GameAchievementRequest>, required): At least one achievement must be provided
     *   - GameAchievementRequest.code (String): Achievement code (1-100 characters, cannot be blank)
     *   - GameAchievementRequest.description (String): Achievement description (1-500 characters, cannot be blank)
     * - playableWithAI (boolean, required): Whether the game can be played with an AI opponent
     *
     * RESPONSE BODY (GameDto):
     * - gameId (UUID): Unique game identifier (auto-generated)
     * - name (String): Game name (echoed from request)
     * - description (String): Game description (echoed from request)
     * - pictureUrl (String): Game image URL (echoed from request)
     * - gameUrl (String): Game playable content URL (echoed from request)
     * - gameCreatorName (String): Creator name (echoed from request)
     * - registrationState (String): Current registration state (e.g., "PENDING", "ACCEPTED", "REJECTED")
     * - rules (List<RuleDto>): List of game rules with descriptions
     * - achievements (List<GameAchievementDto>): List of linked achievements with codes and descriptions
     * - playableWithAI (boolean): Whether the game can be played with an AI opponent (echoed from request)
     *
     * HTTP Status Codes:
     * - 201 Created: Game successfully registered with generated ID
     * - 400 Bad Request: Validation failed (e.g., missing rules/achievements, invalid URLs, text too long/short)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error during game registration
     *  */
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
     * Updates game URLs (picture and game content URLs).
     * Endpoint: PATCH /games/{gameId}/urls
     * Required Role: ADMIN
     *
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to update
     *
     * REQUEST BODY (ModifyGameUrlsRequest):
     * - pictureUrl (String, required): New game picture/thumbnail URL (must be valid URL format, cannot be blank)
     * - gameUrl (String, required): New game playable content URL (must be valid URL format, cannot be blank)
     *
     * RESPONSE BODY (GameDto):
     * - gameId (UUID): Unique identifier of the updated game
     * - name (String): Game name
     * - description (String): Game description
     * - pictureUrl (String): Updated game picture URL
     * - gameUrl (String): Updated game content URL
     * - gameCreatorName (String): Name of the game creator
     * - registrationState (String): Current registration state of the game
     * - rules (List<RuleDto>): List of game rules with descriptions
     * - achievements (List<GameAchievementDto>): List of associated achievements (code and description)
     * - playableWithAI (boolean): Whether the game supports AI opponent mode
     *
     * HTTP Status Codes:
     * - 200 OK: Game URLs successfully updated
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PatchMapping("/{gameId}/urls")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> updateGame(
            @NotNull @PathVariable UUID gameId,
            @Valid @RequestBody ModifyGameUrlsRequest request) {
        Game updatedGame = modifyGameUrlsPort.modifyGameUrls(
                gameMapper.toModifyGameUrlsCommand(gameId, request)
        );
         GameDto response = gameMapper.toResponse(updatedGame);
        return ResponseEntity.ok(response);
    }

    /**
     * Accepts a game, transitioning it from PENDING to ACCEPTED state.
     * Endpoint: PATCH /games/{gameId}/pass
     * Required Role: ADMIN
     *
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to accept and transition to ACCEPTED state
     *
     * RESPONSE BODY (GameDto)
     *
     * HTTP Status Codes:
     * - 200 OK: Game successfully accepted
     * - 400 Bad Request: Invalid state transition (already accepted/rejected)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with given ID doesn't exist
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
     * Endpoint: PATCH /games/{gameId}/fail
     * Required Role: ADMIN
     *
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to reject and transition to REJECTED state
     *
     * RESPONSE BODY (GameDto)
     *
     * HTTP Status Codes:
     * - 200 OK: Game successfully rejected
     * - 400 Bad Request: Invalid state transition (already accepted/rejected)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 404 Not Found: Game with given ID doesn't exist
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


    /**
     * Toggles the AI playability flag of a game between enabled and disabled states.
     * Endpoint: PATCH /games/{gameId}/toggle-playable-ai
     * Required Role: ADMIN
     *
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to toggle AI playability
     *
     * RESPONSE BODY (GameDto):
     *
     * HTTP Status Codes:
     * - 200 OK: AI playability flag successfully toggled
     * - 404 Not Found: Game with given ID doesn't exist
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error during toggle operation
     */
    @PatchMapping("{gameId}/toggle-playable-ai")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity< GameDto> togglePlayableWithAI(
            @NotNull @PathVariable UUID gameId
    ){
        Game game = togglePlayableWithAIPort.togglePlayableWithAI(new TogglePlayableWithAICommand(gameId));
         GameDto response = gameMapper.toResponse(game);
        return ResponseEntity.ok(response);
    }



}

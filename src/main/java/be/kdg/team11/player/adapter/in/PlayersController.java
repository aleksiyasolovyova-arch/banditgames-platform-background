package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.PlayerMapper;
import be.kdg.team11.player.adapter.in.request.ChangePlayerPictureUrlRequest;
import be.kdg.team11.player.adapter.in.response.PlayerDto;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private final CreatePlayerPort createPlayerPort;
    private final ChangeFavouriteGamePort favoriteGamePort;
    private final RemoveFavoriteGamePort unfavoriteGamePort;
    private final PlayerMapper playerMapper;
    private final ChangePlayerPictureUrlPort changePlayerPictureUrlPort;

    /**
     * RESPONSE BODY (PlayerDto)
     * - playerId (UUID): Unique identifier for the created player (from JWT subject claim)
     * - username (String): Username of the player (from JWT preferred_username claim)
     * - pictureUrl (String): URL to the player's profile picture (initially null)
     * - joinedDate (LocalDate): Date when the player joined the platform
     * - unlockedPlatformAchievements (Set<UnlockedPlatformAchievementDto>): Set of platform-wide achievements unlocked by the player
     *   - UnlockedPlatformAchievementDto.achievementId (UUID): ID of the unlocked platform achievement
     *   - UnlockedPlatformAchievementDto.unlockedAt (LocalDateTime): Timestamp when the achievement was unlocked
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Set of game-specific achievements unlocked by the player
     *   - UnlockedGameAchievementDto.gameId (UUID): ID of the game the achievement belongs to
     *   - UnlockedGameAchievementDto.code (String): Code/identifier of the unlocked game achievement
     *   - UnlockedGameAchievementDto.unlockedAt (LocalDateTime): Timestamp when the achievement was unlocked
     * - favoriteGameId (UUID): ID of the player's favorite game (initially null)

     */


    public PlayersController(CreatePlayerPort createPlayerPort,
                             ChangeFavouriteGamePort favoriteGamePort,
                             RemoveFavoriteGamePort unfavoriteGamePort,
                             PlayerMapper playerMapper,
                             ChangePlayerPictureUrlPort changePlayerPictureUrlPort) {
        this.createPlayerPort = createPlayerPort;
        this.favoriteGamePort = favoriteGamePort;
        this.unfavoriteGamePort = unfavoriteGamePort;
        this.playerMapper = playerMapper;
        this.changePlayerPictureUrlPort = changePlayerPictureUrlPort;
    }

    /**
     * Creates a new player profile for the authenticated user.
     * Endpoint: POST /players
     * Required Authentication: Yes (JWT token required)
     * <p>
     * REQUEST BODY: None
     * Authentication is extracted from the JWT token (user ID and username)
     * <p>
     * RESPONSE BODY (PlayerDto)
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Player profile successfully created
     * - 400 Bad Request: Player already exists or invalid token data
     * - 401 Unauthorized: Missing or invalid JWT token
     * - 500 Internal Server Error: Unexpected server error during player creation
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerDto> createPlayer(@AuthenticationPrincipal Jwt token) {
        Player createdPlayer = createPlayerPort.create(
                new CreatePlayerCommand(UUID.fromString(token.getSubject()), token.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
        );
        PlayerDto response = playerMapper.toResponse(createdPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates the current authenticated player's profile picture URL.
     * Endpoint: PUT /players
     * Required Authentication: Authenticated user (JWT token)
     * <p>
     * REQUEST BODY (ChangePlayerPictureUrlRequest):
     * - pictureUrl (String, required): New profile picture URL (cannot be null or blank)
     * <p>
     * RESPONSE BODY (PlayerDto):
     * - strangerUserName (UUID): Unique player identifier (extracted from JWT subject)
     * - username (String): Player username
     * - pictureUrl (String): Updated profile picture URL
     * - joinedDate (LocalDate): Date player joined the platform
     * - unlockedPlatformAchievements (Set<UnlockedAchievementDto>): Set of unlocked platform gameAchievements with unlock timestamps
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Set of unlocked game gameAchievements (gameId, code, unlock timestamp)
     * - favoriteGameId (UUID): ID of the player's favorite game
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Profile picture successfully updated and player data returned
     * - 400 Bad Request: Validation failed (picture URL is null or blank)
     * - 401 Unauthorized: JWT token is invalid, expired, or missing
     * - 404 Not Found: Player with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error during update
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerDto> changePlayerPictureUrl(
            @AuthenticationPrincipal Jwt token,
            @Valid @RequestBody ChangePlayerPictureUrlRequest request
    ) {
        Player updatedPlayer = changePlayerPictureUrlPort.changePictureUrl(
                new ChangePlayerPictureUrlCommand(
                        UUID.fromString(token.getSubject()), request.pictureUrl()
                )
        );
        return ResponseEntity.ok(playerMapper.toResponse(updatedPlayer));
    }

    /**
     * Updates the authenticated player's profile picture URL.
     * Endpoint: PUT /players
     * Required Authentication: Authenticated user (JWT token)
     * <p>
     * REQUEST BODY (ChangePlayerPictureUrlRequest):
     * - pictureUrl (String, required): New profile picture URL (must be non-null and non-blank)
     * <p>
     * RESPONSE BODY (PlayerDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Profile picture successfully updated and player data returned
     * - 400 Bad Request: Validation failed (picture URL is null or blank)
     * - 401 Unauthorized: JWT token is invalid, expired, or missing
     * - 404 Not Found: Player with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during profile picture update
     */
    @PostMapping("/change-favorite-game/{gameId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerDto> changeFavoriteGame(
            @NotNull @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token) {

        UUID playerId = UUID.fromString(token.getSubject());

        Player updatedPlayer = favoriteGamePort.favoriteGame(new ChangeFavoriteGameCommand(playerId, gameId));
        PlayerDto response = playerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }

    /**
     * Player removes a game from their favorites.
     * Endpoint: POST /players/removeFavorite-game
     * Required Authentication: Authenticated user (JWT token)
     * <p>
     * REQUEST BODY: None
     * Authentication is extracted from the JWT token (player ID)
     * <p>
     * RESPONSE BODY (PlayerDto)
     * <p>
     * HTTP Status Codes:
     * - 200 OK: Game successfully removed from favorites and updated player data returned
     * - 401 Unauthorized: JWT token is invalid, expired, or missing
     * - 404 Not Found: Player with the given ID does not exist
     * - 500 Internal Server Error: Unexpected server error during favorite game removal
     */
    @PostMapping("/removeFavorite-game")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerDto> removeFavoriteGame(
            @AuthenticationPrincipal Jwt token) {

        UUID playerId = UUID.fromString(token.getSubject());

        Player updatedPlayer = unfavoriteGamePort.unfavoriteGame(new RemoveFavoriteGameCommand(playerId));
        PlayerDto response = playerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }
}



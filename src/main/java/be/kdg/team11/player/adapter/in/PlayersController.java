package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.PlayerMapper;
import be.kdg.team11.player.adapter.in.request.ChangePlayerPictureUrlRequest;
import be.kdg.team11.player.adapter.in.response.PlayerDto;
import be.kdg.team11.player.adapter.in.response.PlayerInfoDto;
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
     * Creates a new player in the system.
     * FULL PATH: /players (POST)
     * REQUEST BODY (ChangePlayerPictureUrlRequest):
     * - username (String, required): Player username (1-50 chars)
     * - pictureUrl (String, required): URL to player profile picture
     * RESPONSE BODY (PlayerDto):
     * - strangerUserName (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - unlockedPlatformAchievements (Set<UnlockedAchievementDto>): Platform-wide gameAchievements with unlock timestamps
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Game-specific gameAchievements with unlock timestamps
     * - favoriteGameId (UUID): Favourite game ID (null if no favorite games left)
     * HTTP Status Codes:
     * - 201 Created: Player successfully created
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 500 Internal Server Error: Unexpected server error
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
     *
     * REQUEST BODY (ChangePlayerPictureUrlRequest):
     * - pictureUrl (String, required): New profile picture URL (cannot be null or blank)
     *
     * RESPONSE BODY (PlayerDto):
     * - strangerUserName (UUID): Unique player identifier (extracted from JWT subject)
     * - username (String): Player username
     * - pictureUrl (String): Updated profile picture URL
     * - joinedDate (LocalDate): Date player joined the platform
     * - unlockedPlatformAchievements (Set<UnlockedAchievementDto>): Set of unlocked platform gameAchievements with unlock timestamps
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Set of unlocked game gameAchievements (gameId, code, unlock timestamp)
     * - favoriteGameId (UUID): ID of the player's favorite game
     *
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
            ){
        Player updatedPlayer = changePlayerPictureUrlPort.changePictureUrl(
                new ChangePlayerPictureUrlCommand(
                        UUID.fromString(token.getSubject()), request.pictureUrl()
                )
        );
        return ResponseEntity.ok(playerMapper.toResponse(updatedPlayer));
    }

    /**
     * Player marks a game as favorite.
     * FULL PATH: /players/change-favorite-game/{gameId} (POST)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to mark as favorite
     * JWT TOKEN: Extracts strangerUserName from authenticated user
     * RESPONSE BODY (PlayerDto):
     * - strangerUserName (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - unlockedPlatformAchievements (Set<UnlockedAchievementDto>): Player's platform gameAchievements
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Player's game gameAchievements
     * - favoriteGameId (UUID): Favourite game ID (null if no favorite games left)
     * HTTP Status Codes:
     * - 200 OK: Game successfully marked as favorite
     * - 400 Bad Request: Invalid game ID format
     * - 404 Not Found: Player or game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
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
     * Player removes a game from favorites.
     * FULL PATH: /players/removeFavorite-game (POST)
     * JWT TOKEN: Extracts strangerUserName from authenticated user
     * RESPONSE BODY (PlayerDto):
     * - strangerUserName (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - unlockedPlatformAchievements (Set<UnlockedAchievementDto>): Player's platform gameAchievements
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Player's game gameAchievements
     * - favoriteGameId (UUID): Favourite game ID (null if no favorite games left)
     * HTTP Status Codes:
     * - 200 OK: Game successfully removed from favorites
     * - 400 Bad Request: Invalid game ID format
     * - 404 Not Found: Player or game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
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



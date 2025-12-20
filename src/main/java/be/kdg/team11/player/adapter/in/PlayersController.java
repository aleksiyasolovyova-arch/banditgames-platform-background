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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private final CreatePlayerPort createPlayerPort;
    private final BuyGamePort buyGamePort;
    private final FavouriteGamePort favoriteGamePort;
    private final UnfavoriteGamePort unfavoriteGamePort;
    private final PlayerMapper playerMapper;
    private final ChangePlayerPictureUrlPort changePlayerPictureUrlPort;

    public PlayersController(CreatePlayerPort createPlayerPort,
                             BuyGamePort buyGamePort,
                             FavouriteGamePort favoriteGamePort,
                             UnfavoriteGamePort unfavoriteGamePort,
                             PlayerMapper playerMapper,
                             ChangePlayerPictureUrlPort changePlayerPictureUrlPort) {
        this.createPlayerPort = createPlayerPort;
        this.buyGamePort = buyGamePort;
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
     * - playerId (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - ownedGames (Set): Player's owned games (initially all games for testing)
     * - unlockedPlatformAchievements (Set<UnlockedPlatformAchievementDto>): Platform-wide achievements with unlock timestamps
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>):
     * HTTP Status Codes:
     * - 201 Created: Player successfully created
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@AuthenticationPrincipal Jwt token) {
        Player createdPlayer = createPlayerPort.create(
                new CreatePlayerCommand(UUID.fromString(token.getSubject()), token.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
        );
        PlayerDto response = playerMapper.toResponse(createdPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping
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
     * Player buys a game.
     * FULL PATH: /players/buy-game/{gameId} (POST)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to buy
     * JWT TOKEN: Extracts playerId from authenticated user
     * RESPONSE BODY (PlayerDto):
     * - playerId (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - ownedGames (Set<OwnedGameDto>): Updated list of owned games including newly bought game
     * - unlockedPlatformAchievements (Set<UnlockedPlatformAchievementDto>): Player's platform achievements
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Player's game achievements
     * HTTP Status Codes:
     * - 200 OK: Game successfully purchased
     * - 400 Bad Request: Invalid game ID format
     * - 404 Not Found: Player or game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping("/buy-game/{gameId}")
    public ResponseEntity<PlayerDto> buyGame(
            @NotNull @PathVariable UUID gameId) {

        //TODO replace with value from JWT
        UUID playerId = UUID.fromString("4e080e79-d43a-4705-85db-6d44c03f7d81");

        Player updatedPlayer = buyGamePort.buyGame(new BuyGameCommand(playerId, gameId));
        PlayerDto response = playerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }

    /**
     * Player marks a game as favorite.
     * FULL PATH: /players/favorite-game/{gameId} (POST)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to mark as favorite
     * JWT TOKEN: Extracts playerId from authenticated user
     * RESPONSE BODY (PlayerDto):
     * - playerId (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - ownedGames (Set<OwnedGameDto>): Updated list with favorite status set to true for the game
     * - unlockedPlatformAchievements (Set<UnlockedPlatformAchievementDto>): Player's platform achievements
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Player's game achievements
     * HTTP Status Codes:
     * - 200 OK: Game successfully marked as favorite
     * - 400 Bad Request: Invalid game ID format
     * - 404 Not Found: Player or game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping("/favorite-game/{gameId}")
    public ResponseEntity<PlayerDto> favoriteGame(
            @NotNull @PathVariable UUID gameId) {

        //TODO replace with value from JWT
        UUID playerId = UUID.fromString("4e080e79-d43a-4705-85db-6d44c03f7d81");

        Player updatedPlayer = favoriteGamePort.favoriteGame(new FavoriteGameCommand(playerId, gameId));
        PlayerDto response = playerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }

    /**
     * Player removes a game from favorites.
     * FULL PATH: /players/unfavorite-game/{gameId} (POST)
     * PATH PARAMETER:
     * - gameId (UUID): ID of the game to remove from favorites
     * JWT TOKEN: Extracts playerId from authenticated user
     * RESPONSE BODY (PlayerDto):
     * - playerId (UUID): Unique player identifier
     * - username (String): Player username
     * - pictureUrl (String): Player profile picture URL
     * - joinedDate (LocalDate): Date player joined
     * - ownedGames (Set<OwnedGameDto>): Updated list with favorite status set to false for the game
     * - unlockedPlatformAchievements (Set<UnlockedPlatformAchievementDto>): Player's platform achievements
     * - unlockedGameAchievements (Set<UnlockedGameAchievementDto>): Player's game achievements
     * HTTP Status Codes:
     * - 200 OK: Game successfully removed from favorites
     * - 400 Bad Request: Invalid game ID format
     * - 404 Not Found: Player or game with given ID doesn't exist
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping("/unfavorite-game/{gameId}")
    public ResponseEntity<PlayerDto> unfavoriteGame(
            @PathVariable UUID gameId) {
        //TODO replace with value from JWT
        UUID playerId = UUID.fromString("4e080e79-d43a-4705-85db-6d44c03f7d81");

        Player updatedPlayer = unfavoriteGamePort.unfavoriteGame(new UnfavoriteGameCommand(playerId, gameId));
        PlayerDto response = playerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }
}



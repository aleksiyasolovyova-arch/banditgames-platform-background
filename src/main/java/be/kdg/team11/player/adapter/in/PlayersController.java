package be.kdg.team11.player.adapter.in;

import be.kdg.team11.player.adapter.in.mapper.PlayerMapper;
import be.kdg.team11.player.adapter.in.request.CreatePlayerRequest;
import be.kdg.team11.player.adapter.in.response.PlayerDto;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.CreatePlayerPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private final CreatePlayerPort createPlayerPort;
    private final PlayerMapper playerMapper;

    public PlayersController(CreatePlayerPort createPlayerPort, PlayerMapper playerMapper) {
        this.createPlayerPort = createPlayerPort;
        this.playerMapper = playerMapper;
    }

    /**
     * Creates a new player in the system.
     * FULL PATH: /players (POST)
     * REQUEST BODY (CreatePlayerRequest):
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
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        Player createdPlayer = createPlayerPort.create(
                playerMapper.toCreateCommand(request)
        );
        PlayerDto response = playerMapper.toResponse(createdPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

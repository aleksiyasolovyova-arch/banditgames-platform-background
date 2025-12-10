package be.kdg.team11.game.adapter.in;

import be.kdg.team11.game.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.kdg.team11.game.adapter.in.mapper.GameMapper;
import be.kdg.team11.game.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.game.adapter.in.request.UpdateGameRequest;
import be.kdg.team11.game.adapter.in.response.GameDto;
import be.kdg.team11.game.domain.game.Game;
import team11.platform_backend.game.port.in.*;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final RegisterGamePort registerGamePort;
    private final AcceptGamePort acceptGamePort;
    private final RejectGamePort rejectGamePort;
    private final UpdateGamePort updateGamePort;
    private final GameMapper gameMapper;

    public GamesController(RegisterGamePort registerGamePort,
                           AcceptGamePort acceptGamePort,
                           RejectGamePort rejectGamePort,
                           UpdateGamePort updateGamePort,
                           GameMapper gameMapper) {
        this.registerGamePort = registerGamePort;
        this.acceptGamePort = acceptGamePort;
        this.rejectGamePort = rejectGamePort;
        this.updateGamePort = updateGamePort;
        this.gameMapper = gameMapper;
    }

    @PostMapping
    public ResponseEntity<GameDto> createGame(
            @Valid @RequestBody RegisterGameRequest request) {
        Game createdGame = registerGamePort.createGame(
                gameMapper.toCommand(request)
        );
        GameDto response = gameMapper.toResponse(createdGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameDto> updateGame(
            @PathVariable UUID gameId,
            @Valid @RequestBody UpdateGameRequest request) {
        Game updatedGame = updateGamePort.updateGame(
                gameMapper.toUpdateCommand(gameId, request)
        );
        GameDto response = gameMapper.toResponse(updatedGame);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{gameId}/accept")
    public ResponseEntity<GameDto> acceptGame(
            @PathVariable UUID gameId) {
        Game acceptedGame = acceptGamePort.acceptGame(
                new AcceptGameCommand(gameId)
        );
        GameDto response = gameMapper.toResponse(acceptedGame);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{gameId}/reject")
    public ResponseEntity<GameDto> rejectGame(
            @PathVariable UUID gameId) {
        Game rejectedGame = rejectGamePort.rejectGame(
                new RejectGameCommand(gameId)
        );
        GameDto response = gameMapper.toResponse(rejectedGame);
        return ResponseEntity.ok(response);
    }

}

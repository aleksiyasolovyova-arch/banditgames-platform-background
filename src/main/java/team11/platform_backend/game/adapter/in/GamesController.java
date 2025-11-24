package team11.platform_backend.game.adapter.in;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team11.platform_backend.game.adapter.in.mapper.GameMapper;
import team11.platform_backend.game.adapter.in.request.RegisterGameRequest;
import team11.platform_backend.game.adapter.in.response.GameDto;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.port.in.RegisterGamePort;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final RegisterGamePort registerGamePort;
    private final GameMapper gameMapper;

    public GamesController(RegisterGamePort registerGamePort, GameMapper gameMapper) {
        this.registerGamePort = registerGamePort;
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
}

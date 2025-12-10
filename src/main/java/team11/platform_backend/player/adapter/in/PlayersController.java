package team11.platform_backend.player.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team11.platform_backend.player.adapter.in.request.CreatePlayerRequestDto;
import team11.platform_backend.player.adapter.in.response.PlayerResponseDto;
import team11.platform_backend.player.domain.player.Player;
import team11.platform_backend.player.port.in.CreateNewPlayerCommand;
import team11.platform_backend.player.port.in.CreateNewPlayerPort;

@RestController
@RequestMapping("/players")
public class PlayersController {
    private final CreateNewPlayerPort createNewPlayerPort;

    public PlayersController(CreateNewPlayerPort createNewPlayerPort) {
        this.createNewPlayerPort = createNewPlayerPort;
    }

    @PostMapping
    public PlayerResponseDto createPlayer(@RequestBody CreatePlayerRequestDto request) {
        CreateNewPlayerCommand command = new CreateNewPlayerCommand(
                request.playerId()
        );

        Player createdPlayer = createNewPlayerPort.createNewPlayer(command);

        return new PlayerResponseDto(
                createdPlayer.getPlayerId(),
                createdPlayer.getJoinedDate()
        );
    }

}

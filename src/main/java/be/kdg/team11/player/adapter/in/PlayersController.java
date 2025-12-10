package be.kdg.team11.player.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.kdg.team11.player.adapter.in.request.CreatePlayerRequestDto;
import be.kdg.team11.player.adapter.in.response.PlayerResponseDto;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.CreateNewPlayerCommand;
import be.kdg.team11.player.port.in.CreateNewPlayerPort;

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

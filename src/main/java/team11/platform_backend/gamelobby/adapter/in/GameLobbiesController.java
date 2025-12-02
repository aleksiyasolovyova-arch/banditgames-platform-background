package team11.platform_backend.gamelobby.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team11.platform_backend.gamelobby.port.in.JoinMatchMakingPort;

@RestController
@RequestMapping("/game-lobbies")
public class GameLobbiesController {
    private final JoinMatchMakingPort joinMatchMakingPort;

    public GameLobbiesController(
            JoinMatchMakingPort joinMatchMakingPort
    ){
        this.joinMatchMakingPort = joinMatchMakingPort;
    }

    @PostMapping("/{gameId}/join-match-making")
    public Response
}

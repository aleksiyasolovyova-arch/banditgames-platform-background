package team11.platform_backend.gamelobby.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.gamelobby.domain.GameLobby;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;
import team11.platform_backend.gamelobby.port.in.JoinMatchMakingPort;
import team11.platform_backend.gamelobby.port.out.MatchDto;
import team11.platform_backend.gamelobby.port.out.MatchmakingQueuePort;
import team11.platform_backend.gamelobby.port.out.SaveGameLobbyPort;

import java.util.Optional;

@Service
@Transactional
public class JoinMatchMakingUseCaseImpl implements JoinMatchMakingPort {
    private final SaveGameLobbyPort saveGameLobbyPort;
    private final MatchmakingQueuePort matchmakingQueuePort;

    public JoinMatchMakingUseCaseImpl(
            SaveGameLobbyPort saveGameLobbyPort, MatchmakingQueuePort matchmakingQueuePort
    ){
        this.saveGameLobbyPort = saveGameLobbyPort;
        this.matchmakingQueuePort = matchmakingQueuePort;
    }

    @Override
    public Optional<GameLobby> joinMatchMaking(GameId gameId, PlayerId playerId) {
        return matchmakingQueuePort.savePlayerAndMatch(gameId, playerId)
                .map(match -> {
                    GameLobby lobby = GameLobby.createGameLobbyForStrangers(
                            match.gameId(),
                            match.player1(),
                            match.player2()
                    );
                    return saveGameLobbyPort.save(lobby);
                });
    }
}

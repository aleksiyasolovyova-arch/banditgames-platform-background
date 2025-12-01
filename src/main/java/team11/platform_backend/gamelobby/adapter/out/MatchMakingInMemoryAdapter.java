package team11.platform_backend.gamelobby.adapter.out;

import org.springframework.stereotype.Component;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;
import team11.platform_backend.gamelobby.domain.service.MatchmakingService;
import team11.platform_backend.gamelobby.port.out.MatchDto;
import team11.platform_backend.gamelobby.port.out.MatchmakingQueuePort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatchMakingInMemoryAdapter implements MatchmakingQueuePort {

    private final Map<GameId, Deque<PlayerId>> waitingPlayers = new ConcurrentHashMap<>();
    private final MatchmakingService matchmakingService;

    public MatchMakingInMemoryAdapter(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @Override
    public Optional<MatchDto> savePlayerAndMatch(GameId gameId, PlayerId playerId) {
        Deque<PlayerId> queue = waitingPlayers.computeIfAbsent(gameId, id -> new ArrayDeque<>());

        synchronized (queue) {
            Optional<MatchDto> match = matchmakingService.addPlayerAndMatch(gameId, playerId, queue);

            if (matchmakingService.isQueueEmpty(queue)) {
                waitingPlayers.remove(gameId, queue);
            }

            return match;
        }
    }
}

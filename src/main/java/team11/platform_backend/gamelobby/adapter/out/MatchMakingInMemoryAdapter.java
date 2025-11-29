package team11.platform_backend.gamelobby.adapter.out;

import org.springframework.stereotype.Component;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;
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

    @Override
    public Optional<MatchDto> savePlayerAndMatch(GameId gameId, PlayerId playerId) {
        // Get or create the queue for this game
        Deque<PlayerId> queue = waitingPlayers.computeIfAbsent(gameId, id -> new ArrayDeque<>());

        // Only one thread can touch this queue at a time
        synchronized (queue) {
            if (queue.isEmpty()) {
                // Nobody waiting -> current player starts waiting
                queue.addLast(playerId);
                return Optional.empty();
            } else {
                // Someone is waiting -> match them and remove from queue
                PlayerId other = queue.pollFirst();

                // Optional: cleanup empty queue entry
                if (queue.isEmpty()) {
                    waitingPlayers.remove(gameId, queue);
                }

                // Decide order; here other = player1, current = player2
                MatchDto match = new MatchDto(gameId, other, playerId);
                return Optional.of(match);
            }
        }
    }
}

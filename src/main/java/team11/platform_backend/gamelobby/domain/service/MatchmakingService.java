package team11.platform_backend.gamelobby.domain.service;

import org.springframework.stereotype.Service;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;
import team11.platform_backend.gamelobby.port.out.MatchDto;

import java.util.Deque;
import java.util.Optional;

// This can also be a static method inside of gamelobby instead!
@Service
public class MatchmakingService {
    public Optional<MatchDto> addPlayerAndMatch(GameId gameId,
                                                PlayerId playerId,
                                                Deque<PlayerId> queue) {
        if (queue.isEmpty()) {
            queue.addLast(playerId);
            return Optional.empty();
        } else {
            PlayerId other = queue.pollFirst();
            return Optional.of(new MatchDto(gameId, other, playerId));
        }
    }

    public boolean isQueueEmpty(Deque<PlayerId> queue) {
        return queue.isEmpty();
    }
}


package be.kdg.team11.acl.chess.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record ChessAchievementAcquiredEvent(
        @JsonProperty("gameId") String gameId,
        @JsonProperty("playerId") String playerId,
        @JsonProperty("playerName") String playerName,
        @JsonProperty("achievementType") String achievementType,
        @JsonProperty("achievementDescription") String achievementDescription,
        @JsonProperty("messageType") String messageType,
        @JsonProperty("timestamp") Instant timestamp
) {}
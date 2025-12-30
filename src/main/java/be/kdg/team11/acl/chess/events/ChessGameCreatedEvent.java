package be.kdg.team11.acl.chess.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record ChessGameCreatedEvent(
        @JsonProperty("gameId") String gameId,
        @JsonProperty("whitePlayer") String whitePlayer,
        @JsonProperty("blackPlayer") String blackPlayer,
        @JsonProperty("currentFen") String currentFen,
        @JsonProperty("status") String status,
        @JsonProperty("messageType") String messageType,
        @JsonProperty("timestamp") Instant timestamp
) {}

package be.kdg.team11.acl.chess.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record ChessGameEndedEvent(
        @JsonProperty("gameId") String gameId,
        @JsonProperty("whitePlayer") String whitePlayer,
        @JsonProperty("blackPlayer") String blackPlayer,
        @JsonProperty("finalFen") String finalFen,
        @JsonProperty("endReason") String endReason,  // CHECKMATE or DRAW
        @JsonProperty("winner") String winner,  // WHITE, BLACK, or DRAW
        @JsonProperty("totalMoves") int totalMoves,
        @JsonProperty("messageType") String messageType,
        @JsonProperty("timestamp") Instant timestamp
) {}
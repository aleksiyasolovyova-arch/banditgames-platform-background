package be.kdg.team11.acl.chess.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

public record ChessGameRegisteredEvent(
        @JsonProperty("registrationId") String registrationId,
        @JsonProperty("frontendUrl") String frontendUrl,
        @JsonProperty("pictureUrl") String pictureUrl,
        @JsonProperty("availableAchievements") List<ChessAchievement> availableAchievements,
        @JsonProperty("messageType") String messageType,
        @JsonProperty("timestamp") Instant timestamp
) {
    public record ChessAchievement(
            @JsonProperty("code") String code,
            @JsonProperty("description") String description
    ) {}
}

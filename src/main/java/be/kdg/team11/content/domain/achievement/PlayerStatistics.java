// game/domain/achievement/PlayerStatistics.java
package be.kdg.team11.content.domain.achievement;


import java.util.UUID;

public record PlayerStatistics(
        Long totalGamesPlayed,
        Long totalWins,
        Long totalFriends,
        Long bestRecordTime,
        UUID playerId
) {
    // For each event type add a mapper method
}
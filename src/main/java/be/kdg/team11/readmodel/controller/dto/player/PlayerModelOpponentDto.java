package be.kdg.team11.readmodel.controller.dto.player;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerModelOpponentDto(
        UUID playerID,
        String username,
        String pictureUrl,
        LocalDate joinedDate,

        //statistics
        Integer totalGamesPlayed,
        Integer totalWins,
        Integer totalLosses,
        Integer totalDraws,
        Double winRatePercentage,

        Integer totalPlaytimeMinutes,
        Double totalHoursPlayed,

        Integer longestWinningStreak,
        Integer currentWinningStreak,

        Integer firstMoveGames,
        Integer firstMoveWins,
        Double firstMoveWinRatePercentage,
        Integer secondMoveGames,
        Integer secondMoveWins,
        Double secondMoveWinRatePercentage


) implements PlayerModelDto {
}

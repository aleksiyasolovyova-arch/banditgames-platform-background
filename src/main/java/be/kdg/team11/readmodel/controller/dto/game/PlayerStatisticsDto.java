package be.kdg.team11.readmodel.controller.dto.game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PlayerStatisticsDto(

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

) {
}

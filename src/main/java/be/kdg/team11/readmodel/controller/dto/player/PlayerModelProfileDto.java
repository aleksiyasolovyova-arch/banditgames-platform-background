package be.kdg.team11.readmodel.controller.dto.player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlayerModelProfileDto(
        UUID playerId,
        String username,
        String pictureUrl,

        LocalDate joinedDate,
        LocalDateTime lastActive,

        UUID favouriteGameId,
        String favouriteGameName,
        String favouriteGamePictureUrl,

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
        Double secondMoveWinRatePercentage,

        List<PlayerHistoryDto> games
) implements PlayerModelDto {
    public record PlayerHistoryDto(
            UUID lobbyId,
            UUID gameId,
            String gameName,
            String pictureUrl,
            UUID opponentId,
            String opponentUsername,
            String opponentPictureUrl,
            String result,
            Integer durationMinutes,
            LocalDateTime startedAt,
            LocalDateTime finishedAt
    ) {
    }

}

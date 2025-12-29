package be.kdg.team11.readmodel.controller.dto.player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//removed game stats dto because i read a few articles saying to keep dtos flat and simple and
//avoid nested objects in them if possible
public record PlayerProfileDto(
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
) {
    public record PlayerHistoryDto(
            UUID lobbyId,
            UUID gameId,
            String gameName,
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

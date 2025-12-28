package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.controller.dto.PlayerModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerHistoryDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerProfileDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerStatisticsDto;
import be.kdg.team11.readmodel.models.LobbyModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PlayerModelMapper {
    public PlayerModelDto toDto(PlayerModel player) {
        return new PlayerModelDto(
                player.getPlayerId(),
                player.getUsername(),
                player.getPictureUrl(),
                player.getJoinedDate(),
                player.getFavouriteGameId());
    }

    public PlayerStatisticsDto toStatisticsDto(PlayerModel player) {
        return new PlayerStatisticsDto(
                player.getTotalGamesPlayed(),
                player.getTotalWins(),
                player.getTotalLosses(),
                player.getTotalDraws(),
                calculateWinRate(player),

                player.getTotalPlaytimeMinutes(),
                calculateTotalHours(player),

                player.getLongestWinningStreak(),
                player.getCurrentWinningStreak(),

                player.getFirstMoveGames(),
                player.getFirstMoveWins(),
                calculateFirstMoveWinRate(player),

                player.getSecondMoveGames(),
                player.getSecondMoveWins(),
                calculateSecondMoveWinRate(player)
        );
    }

        public PlayerHistoryDto toHistoryDto(LobbyModel lobby, UUID playerId) {
            boolean isPlayer1 = playerId.equals(lobby.getPlayer1Id());

            UUID opponentId = isPlayer1 ? lobby.getPlayer2Id() : lobby.getPlayer1Id();
            String opponentUsername = isPlayer1 ? lobby.getPlayer2Username() : lobby.getPlayer1Username();
            String opponentPictureUrl = isPlayer1 ? lobby.getPlayer2PictureUrl() : lobby.getPlayer1PictureUrl();

            String result = determinePlayerResult(lobby, playerId);

            int durationMinutes = calculateLobbyDuration(lobby);

            return new PlayerHistoryDto(
                    lobby.getLobbyId(),
                    lobby.getGameId(),
                    lobby.getGameName(),
                    opponentId,
                    opponentUsername,
                    opponentPictureUrl,
                    result,
                    durationMinutes,
                    lobby.getStartedAt(),
                    lobby.getFinishedAt()
            );
        }

    public PlayerProfileDto toProfileDto(
            PlayerModel player,
            List<LobbyModel> gameHistory,
            String favouriteGameName) {

        PlayerStatisticsDto statistics = toStatisticsDto(player);

        List<PlayerHistoryDto> history = gameHistory.stream()
                .map(lobby -> toHistoryDto(lobby, player.getPlayerId()))
                .toList();

        return new PlayerProfileDto(
                player.getPlayerId(),
                player.getUsername(),
                player.getPictureUrl(),
                player.getJoinedDate(),
                player.getLastActive(),

                player.getFavouriteGameId(),
                favouriteGameName,

                statistics,
                history
        );
    }

    private String determinePlayerResult(LobbyModel lobby, UUID playerId) {
        if ("DRAW".equals(lobby.getResult())) {
            return "DRAW";
        }

        if (lobby.getWinnerId() != null) {
            return playerId.equals(lobby.getWinnerId()) ? "WIN" : "LOSS";
        }

        return "UNKNOWN";
    }

    private double calculateWinRate(PlayerModel player) {
        if (player.getTotalGamesPlayed() == 0) {
            return 0.0;
        }
        return (double) player.getTotalWins() / player.getTotalGamesPlayed() * 100.0;
    }

    private double calculateFirstMoveWinRate(PlayerModel player) {
        if (player.getFirstMoveGames() == 0) {
            return 0.0;
        }
        return (double) player.getFirstMoveWins() / player.getFirstMoveGames() * 100.0;
    }

    private double calculateSecondMoveWinRate(PlayerModel player) {
        if (player.getSecondMoveGames() == 0) {
            return 0.0;
        }
        return (double) player.getSecondMoveWins() / player.getSecondMoveGames() * 100.0;
    }

    private double calculateTotalHours(PlayerModel player) {
        return player.getTotalPlaytimeMinutes() / 60.0;
    }

    private int calculateLobbyDuration(LobbyModel lobby) {
        if (lobby.getStartedAt() == null || lobby.getFinishedAt() == null) {
            return 0;
        }
        Duration duration = Duration.between(
                lobby.getStartedAt(),
                lobby.getFinishedAt()
        );
        return (int) duration.toMinutes();
    }
}


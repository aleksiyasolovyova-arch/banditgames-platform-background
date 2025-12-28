package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.controller.dto.PlayerModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerProfileDto;
import be.kdg.team11.readmodel.models.LobbyModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.LobbyModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedPictureUrlEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerCreatedEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PlayerModelServiceImpl implements PlayerModelService {
    private final PlayerModelRepository playerModelRepository;
    private final PlayerModelMapper playerModelMapper;
    private final LobbyModelRepository lobbyModelRepository;
    private final GameModelRepository gameModelRepository;

    public PlayerModelServiceImpl(PlayerModelRepository playerModelRepository,
                                  PlayerModelMapper playerModelMapper,
                                  LobbyModelRepository lobbyModelRepository,
                                  GameModelRepository gameModelRepository) {
        this.playerModelRepository = playerModelRepository;
        this.playerModelMapper = playerModelMapper;
        this.lobbyModelRepository = lobbyModelRepository;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public void project(PlayerCreatedEvent event) {
        PlayerModel player = new PlayerModel();
        player.setPlayerId(event.playerId());
        player.setUsername(event.username());
        player.setPictureUrl(event.pictureUrl());
        player.setJoinedDate(event.joinedDate());

        playerModelRepository.save(player);
    }

    @Override
    public void project(PlayerChangedPictureUrlEvent event) {
        playerModelRepository.findById(event.playerId())
                .ifPresent(player -> {
                    player.setPictureUrl(event.pictureUrl());
                    playerModelRepository.save(player);
                });
    }

    @Override
    public void project(PlayerChangedFavoriteGameEvent event) {
        playerModelRepository.findById(event.playerId())
                .ifPresent(player -> {
                    player.setFavouriteGameId(event.gameId());
                    playerModelRepository.save(player);
                });
    }

    @Override
    public void project(PlayerRemovedFavoriteGameEvent event) {
        playerModelRepository.findById(event.playerId())
                .ifPresent(player -> {
                    player.setFavouriteGameId(null);
                    playerModelRepository.save(player);
                });
    }

    @Override
    public void project(LobbyEndedWithWinnerEvent event) {
        Optional<LobbyModel> lobbyOpt = lobbyModelRepository.findById(event.lobbyId());

        if (lobbyOpt.isEmpty()) {
            return;
        }

        LobbyModel lobby = lobbyOpt.get();
        UUID winnerId = event.winnerId();
        UUID loserId = winnerId.equals(lobby.getPlayer1Id())
                ? lobby.getPlayer2Id()
                : lobby.getPlayer1Id();

        int durationMinutes = calculateDuration(lobby);
        int winnerPosition = winnerId.equals(lobby.getPlayer1Id()) ? 1 : 2;
        int loserPosition = winnerPosition == 1 ? 2 : 1;

        playerModelRepository.findById(winnerId)
                .ifPresent(player -> {
                    updatePlayerStatsForWin(player, lobby, winnerPosition, durationMinutes);
                    playerModelRepository.save(player);
                });

        playerModelRepository.findById(loserId)
                .ifPresent(player -> {
                    updatePlayerStatsForLoss(player, lobby, loserPosition, durationMinutes);
                    playerModelRepository.save(player);
                });
    }

    @Override
    public void project(LobbyEndedWithDrawEvent event) {
        Optional<LobbyModel> lobbyOpt = lobbyModelRepository.findById(event.lobbyId());

        if (lobbyOpt.isEmpty()) {
            return;
        }

        LobbyModel lobby = lobbyOpt.get();
        int durationMinutes = calculateDuration(lobby);

        playerModelRepository.findById(lobby.getPlayer1Id())
                .ifPresent(player -> {
                    updatePlayerStatsForDraw(player, lobby, 1, durationMinutes);
                    playerModelRepository.save(player);
                });


        playerModelRepository.findById(lobby.getPlayer2Id())
                .ifPresent(player -> {
                    updatePlayerStatsForDraw(player, lobby, 2, durationMinutes);
                    playerModelRepository.save(player);
                });
    }

    private void updatePlayerStatsForWin(
            PlayerModel player,
            LobbyModel lobby,
            int playerPosition,
            int durationMinutes) {

        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);
        player.setTotalWins(player.getTotalWins() + 1);
        player.setTotalPlaytimeMinutes(player.getTotalPlaytimeMinutes() + durationMinutes);
        player.setLastActive(lobby.getFinishedAt());


        int newStreak = player.getCurrentWinningStreak() + 1;
        player.setCurrentWinningStreak(newStreak);
        if (newStreak > player.getLongestWinningStreak()) {
            player.setLongestWinningStreak(newStreak);
        }

        updateFirstMoveStats(player, playerPosition, true);
    }

    private void updatePlayerStatsForLoss(
            PlayerModel player,
            LobbyModel lobby,
            int playerPosition,
            int durationMinutes) {

        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);
        player.setTotalLosses(player.getTotalLosses() + 1);
        player.setTotalPlaytimeMinutes(player.getTotalPlaytimeMinutes() + durationMinutes);
        player.setLastActive(lobby.getFinishedAt());

        player.setCurrentWinningStreak(0);

        updateFirstMoveStats(player, playerPosition, false);
    }

    private void updatePlayerStatsForDraw(
            PlayerModel player,
            LobbyModel lobby,
            int playerPosition,
            int durationMinutes) {

        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);
        player.setTotalDraws(player.getTotalDraws() + 1);
        player.setTotalPlaytimeMinutes(player.getTotalPlaytimeMinutes() + durationMinutes);
        player.setLastActive(lobby.getFinishedAt());

        player.setCurrentWinningStreak(0);
        updateFirstMoveStats(player, playerPosition, false);

    }

    private int calculateDuration(LobbyModel lobby) {
        if (lobby.getStartedAt() == null || lobby.getFinishedAt() == null) {
            return 0;
        }
        Duration duration = Duration.between(lobby.getStartedAt(), lobby.getFinishedAt());
        return (int) duration.toMinutes();
    }


    private void updateFirstMoveStats(PlayerModel player, int playerPosition, boolean won) {
        if (playerPosition == 1) {
            player.setFirstMoveGames(player.getFirstMoveGames() + 1);
            if (won) {
                player.setFirstMoveWins(player.getFirstMoveWins() + 1);
            }
        } else if (playerPosition == 2) {
            player.setSecondMoveGames(player.getSecondMoveGames() + 1);
            if (won) {
                player.setSecondMoveWins(player.getSecondMoveWins() + 1);
            }
        }
    }


    @Override
    public Optional<PlayerModelDto> findByPlayerId(UUID playerId) {

        return playerModelRepository.findById(playerId).map(playerModelMapper::toDto);
    }

    @Override
    public PlayerProfileDto getPlayerProfile(UUID playerId) {
        PlayerModel playerModel = playerModelRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found: " + playerId));

        List<LobbyModel> gameHistory = lobbyModelRepository
                .findByPlayer1IdOrPlayer2IdOrderByFinishedAtDesc(playerId, playerId);

        String favouriteGameName = null;
        if (playerModel.getFavouriteGameId() != null) {
            favouriteGameName = gameModelRepository.findById(playerModel.getFavouriteGameId())
                    .map(game -> game.getName())
                    .orElse(null);
        }
        return playerModelMapper.toProfileDto(playerModel, gameHistory, favouriteGameName);

    }
}

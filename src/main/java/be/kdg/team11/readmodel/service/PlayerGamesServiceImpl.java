package be.kdg.team11.readmodel.service;

import be.kdg.team11.readmodel.controller.response.PlayerGamesDto;
import be.kdg.team11.readmodel.models.GameRM;
import be.kdg.team11.readmodel.models.PlayerFavouriteGameRM;
import be.kdg.team11.readmodel.repository.GameRMRepository;
import be.kdg.team11.readmodel.repository.PlayerFavouriteGameRMRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerGamesServiceImpl implements PlayerGamesService {
    private final GameRMRepository gameRMRepository;
    private final PlayerFavouriteGameRMRepository playerFavouriteGameRMRepository;

    public PlayerGamesServiceImpl(GameRMRepository gameRMRepository, PlayerFavouriteGameRMRepository playerFavouriteGameRMRepository) {
        this.gameRMRepository = gameRMRepository;
        this.playerFavouriteGameRMRepository = playerFavouriteGameRMRepository;
    }

    @Override
    public List<PlayerGamesDto> getAllForPlayerId(UUID playerId) {
        // Get all games from the read model
        List<GameRM> allGames = gameRMRepository.findAll();

        // Get the player's single favourite game (if exists)
        Optional<PlayerFavouriteGameRM> playerFavourite = playerFavouriteGameRMRepository.findById(playerId);
        UUID favouriteGameId = playerFavourite.map(PlayerFavouriteGameRM::getGameId).orElse(null);

        // Map GameRM to PlayerGamesDto with isFavourite flag
        return allGames.stream()
                .map(game -> new PlayerGamesDto(
                        game.getGameId(),
                        game.getName(),
                        game.getDescription(),
                        game.getPictureUrl(),
                        game.getGameUrl(),
                        game.getGameCreatorName(),
                        game.getRules().stream()
                                .map(PlayerGamesDto.RuleDto::new)
                                .toList(),
                        game.getGameId().equals(favouriteGameId)
                ))
                .toList();
    }

}

package be.kdg.team11.readmodel.service.playergames;

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
        List<GameRM> allGames = gameRMRepository.findAll();

        Optional<PlayerFavouriteGameRM> playerFavourite = playerFavouriteGameRMRepository.findById(playerId);
        UUID favouriteGameId = playerFavourite.map(PlayerFavouriteGameRM::getGameId).orElse(null);

        return allGames.stream()
                .map(game -> new PlayerGamesDto(
                        game.getGameId(),
                        game.getName(),
                        game.getDescription(),
                        game.getPictureUrl(),
                        game.getGameUrl(),
                        game.getGameCreatorName(),
                        game.getRules(),
                        game.getGameId().equals(favouriteGameId),
                        game.isPlayableWithAI()
                ))
                .toList();
    }

}

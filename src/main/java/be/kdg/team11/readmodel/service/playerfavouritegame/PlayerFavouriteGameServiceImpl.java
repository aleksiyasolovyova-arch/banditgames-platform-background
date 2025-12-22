package be.kdg.team11.readmodel.service.playerfavouritegame;

import be.kdg.team11.readmodel.models.PlayerFavouriteGameRM;
import be.kdg.team11.readmodel.repository.PlayerFavouriteGameRMRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class PlayerFavouriteGameServiceImpl implements PlayerFavouriteGameService {
    private final PlayerFavouriteGameRMRepository playerFavouriteGameRMRepository;

    public PlayerFavouriteGameServiceImpl(PlayerFavouriteGameRMRepository playerFavouriteGameRMRepository) {
        this.playerFavouriteGameRMRepository = playerFavouriteGameRMRepository;
    }

    @Override
    public void project (UUID playerId, UUID gameId){
        PlayerFavouriteGameRM entity = playerFavouriteGameRMRepository
                .findById(playerId)
                .orElse(new PlayerFavouriteGameRM());
        entity.setPlayerId(playerId);
        entity.setGameId(gameId);
        playerFavouriteGameRMRepository.save(entity);
    }

    @Override
    public void project(UUID playerId) {
        playerFavouriteGameRMRepository.findById(playerId)
                .ifPresent(playerFavouriteGameRMRepository::delete);
    }
}

package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PlayerModelServiceImpl implements PlayerModelService {
    private final PlayerModelRepository playerModelRepository;

    public PlayerModelServiceImpl(PlayerModelRepository playerModelRepository) {
        this.playerModelRepository = playerModelRepository;
    }

    @Override
    public void project (UUID playerId, UUID gameId){
        PlayerModel entity = playerModelRepository
                .findById(playerId)
                .orElse(new PlayerModel());
        entity.setPlayerId(playerId);
        entity.setFavouriteGameId(gameId);
        playerModelRepository.save(entity);
    }

    @Override
    public void project(UUID playerId) {
        playerModelRepository.findById(playerId)
                .ifPresent(playerModelRepository::delete);
    }

    @Override
    public Optional<PlayerModel> findByPlayerId(UUID playerId) {
        return playerModelRepository.findById(playerId);
    }
}

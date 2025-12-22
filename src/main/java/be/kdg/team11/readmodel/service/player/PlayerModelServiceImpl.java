package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedFavoriteGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerChangedPictureUrlEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerCreatedEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerRemovedFavoriteGameEvent;
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
    public void project(PlayerCreatedEvent event) {
        PlayerModel player = new PlayerModel();
        player.setPlayerId(event.playerId());
        player.setUsername(event.username());
        player.setPictureUrl(event.pictureUrl());
        player.setJoinedDate(event.joinedDate());
        player.setCreatedAt(event.eventPit());

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
    public Optional<PlayerModel> findByPlayerId(UUID playerId) {
        return playerModelRepository.findById(playerId);
    }
}

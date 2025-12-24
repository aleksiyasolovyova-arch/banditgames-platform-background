package be.kdg.team11.readmodel.service.player;

import be.kdg.team11.readmodel.controller.dto.PlayerModelDto;
import be.kdg.team11.readmodel.models.PlayerModel;
import org.springframework.stereotype.Component;

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
}

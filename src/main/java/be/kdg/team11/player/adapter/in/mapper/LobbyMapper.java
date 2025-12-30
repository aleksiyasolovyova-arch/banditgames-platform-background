package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.response.LobbyDto;
import be.kdg.team11.player.domain.lobby.Lobby;
import org.springframework.stereotype.Component;

@Component
public class LobbyMapper {
    public LobbyDto toDto(Lobby lobby) {

        return new LobbyDto(
                lobby.getLobbyId().lobbyId(),
                lobby.getGameReference().gameId(),
                lobby.getPlayerIdPair().getFirst().playerId(),
                lobby.getPlayerIdPair().getSecond().playerId(),
                lobby.getStartTime(),
                lobby.getEndTime(),
                lobby.getLobbyResult().toString(),
                lobby.getLink()
        );
    }
}

package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.response.LobbyDto;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.Slot;
import org.springframework.stereotype.Component;

@Component
public class LobbyMapper {
    public LobbyDto toDto(Lobby lobby) {
        Slot slot1 = lobby.getSlotPair().getFirst();
        Slot slot2 = lobby.getSlotPair().getSecond();

        return new LobbyDto(
                lobby.getLobbyId().lobbyId(),
                lobby.getGameReference().gameId(),
                lobby.getLobbyResult().toString(),
                slot1.getPlayerId().playerId(),
                slot1.getParticipationStatus().toString(),
                slot2.getPlayerId().playerId(),
                slot2.getParticipationStatus().toString(),
                lobby.getStartTime(),
                lobby.getEndTime(),
                lobby.getLobbyResult().toString()
        );
    }
}

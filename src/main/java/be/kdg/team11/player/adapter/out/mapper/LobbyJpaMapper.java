package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.entity.LobbyJpaEntity;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class LobbyJpaMapper {

    public LobbyJpaEntity toJpaEntity(Lobby lobby) {
        LobbyJpaEntity entity = new LobbyJpaEntity();

        entity.setLobbyId(lobby.getLobbyId().lobbyId());
        entity.setGameId(lobby.getGameReference().gameId());

        entity.setPlayer1Id(lobby.getPlayerIdPair().getFirst().playerId());
        entity.setPlayer2Id(lobby.getPlayerIdPair().getSecond().playerId());

        entity.setLobbyResult(lobby.getLobbyResult());
        entity.setStartTime(lobby.getStartTime());
        entity.setEndTime(lobby.getEndTime());

        return entity;
    }

    public Lobby toDomain(LobbyJpaEntity entity) {
        LobbyId lobbyId = LobbyId.of(entity.getLobbyId());
        GameReference gameReference = GameReference.of(entity.getGameId(), entity.getGameUrl());
        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(PlayerId.of(entity.getPlayer1Id()), PlayerId.of(entity.getPlayer2Id()));

        return new Lobby(
                lobbyId,
                gameReference,
                playerIdPair,
                entity.getLobbyResult(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
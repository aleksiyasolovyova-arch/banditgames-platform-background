package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.entity.LobbyJpaEntity;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.AvailableGame;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class LobbyJpaMapper {

    public LobbyJpaEntity toJpaEntity(Lobby lobby) {
        LobbyJpaEntity entity = new LobbyJpaEntity();

        entity.setGameLobbyId(lobby.getLobbyId().gameLobbyId());
        entity.setGameReference(lobby.getGameId().gameId());
        entity.setPlayerId1(lobby.getSlotPair().getFirst().playerId());
        entity.setPlayerId2(lobby.getSlotPair().getSecond().playerId());
        entity.setPlayer1Accepted(lobby.getPlayer1Accepted());
        entity.setPlayer2Accepted(lobby.getPlayer2Accepted());
        entity.setGameLobbyStatus(lobby.getGameLobbyStatus());
        entity.setGameLobbyResult(lobby.getLobbyResult());
        entity.setStartTime(lobby.getStartTime());
        entity.setEndTime(lobby.getEndTime());

        return entity;
    }

    public Lobby toDomain(LobbyJpaEntity entity) {
        LobbyId lobbyId = new LobbyId(entity.getGameLobbyId());
        AvailableGame gameId = new AvailableGame(entity.getGameReference());
        PlayerId playerId1 = new PlayerId(entity.getPlayerId1());
        PlayerId playerId2 = new PlayerId(entity.getPlayerId2());
        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(playerId1, playerId2);

        return new Lobby(
                lobbyId,
                gameId,
                playerIdPair,
                entity.getPlayer1Accepted(),
                entity.getPlayer2Accepted(),
                entity.getGameLobbyStatus(),
                entity.getGameLobbyResult(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
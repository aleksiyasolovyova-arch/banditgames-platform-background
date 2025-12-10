package team11.platform_backend.player.adapter.out.mapper;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import team11.platform_backend.player.adapter.out.jpa.entity.GameLobbyJpaEntity;
import team11.platform_backend.player.domain.gamelobby.GameLobby;
import team11.platform_backend.player.domain.gamelobby.GameLobbyId;
import team11.platform_backend.player.domain.player.PlayerId;
import team11.platform_backend.player.domain.projections.GameId;

@Component
public class GameLobbyJpaMapper {

    public GameLobbyJpaEntity toJpaEntity(GameLobby gameLobby) {
        GameLobbyJpaEntity entity = new GameLobbyJpaEntity();

        entity.setGameLobbyId(gameLobby.getGameLobbyId().gameLobbyId());
        entity.setGameId(gameLobby.getGameId().gameId());
        entity.setPlayerId1(gameLobby.getPlayerIdPair().getFirst().playerId());
        entity.setPlayerId2(gameLobby.getPlayerIdPair().getSecond().playerId());
        entity.setPlayer1Accepted(gameLobby.getPlayer1Accepted());
        entity.setPlayer2Accepted(gameLobby.getPlayer2Accepted());
        entity.setGameLobbyStatus(gameLobby.getGameLobbyStatus());
        entity.setGameLobbyResult(gameLobby.getGameLobbyResult());
        entity.setStartTime(gameLobby.getStartTime());
        entity.setEndTime(gameLobby.getEndTime());

        return entity;
    }

    public GameLobby toDomain(GameLobbyJpaEntity entity) {
        GameLobbyId gameLobbyId = new GameLobbyId(entity.getGameLobbyId());
        GameId gameId = new GameId(entity.getGameId());
        PlayerId playerId1 = new PlayerId(entity.getPlayerId1());
        PlayerId playerId2 = new PlayerId(entity.getPlayerId2());
        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(playerId1, playerId2);

        return new GameLobby(
                gameLobbyId,
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
package team11.platform_backend.gamelobby.adapter.out.jpa;

import org.springframework.stereotype.Component;
import team11.platform_backend.gamelobby.domain.GameLobby;
import team11.platform_backend.gamelobby.domain.GameLobbyId;
import team11.platform_backend.gamelobby.domain.GameResult;
import team11.platform_backend.gamelobby.domain.projections.GameId;
import team11.platform_backend.gamelobby.domain.projections.PlayerId;

@Component
public class GameLobbyJpaMapper {

    public GameLobbyJpaEntity toJpaEntity(GameLobby gameLobby) {
        GameLobbyJpaEntity entity = new GameLobbyJpaEntity();

        // Assuming GameLobbyId/GameId/PlayerId expose uuid()
        entity.setGameLobbyId(
                gameLobby.getGameLobbyId() != null ? gameLobby.getGameLobbyId().gameLobbyId() : null
        );
        entity.setGameId(gameLobby.getGameId().gameId());
        entity.setPlayerId1(gameLobby.getPlayerId1().playerId());
        entity.setPlayerId2(gameLobby.getPlayerId2().playerId());
        entity.setPlayer1Accepted(gameLobby.isPlayer1Accepted());
        entity.setPlayer2Accepted(gameLobby.isPlayer2Accepted());
        entity.setGameResult(gameLobby.getGameResult());
        entity.setStartTime(gameLobby.getStartTime());
        entity.setEndTime(gameLobby.getEndTime());

        return entity;
    }

    public GameLobby toDomain(GameLobbyJpaEntity entity) {
        GameLobbyId gameLobbyId = new GameLobbyId(entity.getGameLobbyId());
        GameId gameId = new GameId(entity.getGameId());
        PlayerId playerId1 = new PlayerId(entity.getPlayerId1());
        PlayerId playerId2 = new PlayerId(entity.getPlayerId2());
        GameResult gameResult = entity.getGameResult();

        return new GameLobby(
                gameLobbyId,
                gameId,
                playerId1,
                playerId2,
                entity.getEndTime(),
                entity.isPlayer1Accepted(),
                entity.isPlayer2Accepted(),
                gameResult,
                entity.getStartTime()
        );
    }
}

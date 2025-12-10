package be.kdg.team11.player.adapter.out;

import org.springframework.stereotype.Component;
import be.kdg.team11.player.adapter.out.jpa.entity.GameLobbyJpaEntity;
import be.kdg.team11.player.adapter.out.mapper.GameLobbyJpaMapper;
import be.kdg.team11.player.adapter.out.jpa.GameLobbyJpaRepository;
import be.kdg.team11.player.domain.gamelobby.GameLobby;
import be.kdg.team11.player.domain.gamelobby.GameLobbyId;
import be.kdg.team11.player.port.out.LoadGameLobbiesPort;
import be.kdg.team11.player.port.out.LoadGameLobbyPort;
import be.kdg.team11.player.port.out.SaveGameLobbyPort;

import java.util.List;
import java.util.Optional;

@Component
public class GameLobbyJpaAdapter implements SaveGameLobbyPort, LoadGameLobbyPort , LoadGameLobbiesPort {
    private final GameLobbyJpaRepository gameLobbyJpaRepository;
    private final GameLobbyJpaMapper gameLobbyJpaMapper;

    public GameLobbyJpaAdapter(
            GameLobbyJpaRepository gameLobbyJpaRepository,
            GameLobbyJpaMapper gameLobbyJpaMapper
    ){
        this.gameLobbyJpaRepository = gameLobbyJpaRepository;
        this.gameLobbyJpaMapper = gameLobbyJpaMapper;
    }

    @Override
    public List<GameLobby> loadAll() {
        return gameLobbyJpaRepository.findAll().stream()
                .map(gameLobbyJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameLobby> loadBy(GameLobbyId gameLobbyId) {
        return gameLobbyJpaRepository.findById(gameLobbyId.gameLobbyId())
                .map(gameLobbyJpaMapper::toDomain);
    }

    @Override
    public GameLobby save(GameLobby gameLobby) {
        GameLobbyJpaEntity entity = gameLobbyJpaMapper.toJpaEntity(gameLobby);
        GameLobbyJpaEntity saved = gameLobbyJpaRepository.save(entity);
        return gameLobbyJpaMapper.toDomain(saved);
    }
}

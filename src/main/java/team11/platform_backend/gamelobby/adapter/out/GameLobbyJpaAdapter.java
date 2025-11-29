package team11.platform_backend.gamelobby.adapter.out;

import org.springframework.stereotype.Component;
import team11.platform_backend.gamelobby.adapter.out.jpa.GameLobbyJpaEntity;
import team11.platform_backend.gamelobby.adapter.out.jpa.GameLobbyJpaMapper;
import team11.platform_backend.gamelobby.adapter.out.jpa.GameLobbyJpaRepository;
import team11.platform_backend.gamelobby.domain.GameLobby;
import team11.platform_backend.gamelobby.domain.GameLobbyId;
import team11.platform_backend.gamelobby.port.out.LoadGameLobbiesPort;
import team11.platform_backend.gamelobby.port.out.LoadGameLobbyPort;
import team11.platform_backend.gamelobby.port.out.SaveGameLobbyPort;

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
    public List<GameLobby> loadAllGameLobbies() {
        return gameLobbyJpaRepository.findAll().stream()
                .map(gameLobbyJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameLobby> loadById(GameLobbyId gameLobbyId) {
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

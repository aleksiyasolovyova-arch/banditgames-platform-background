package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.GameLobbyJpaRepository;
import be.kdg.team11.player.adapter.out.jpa.entity.GameLobbyJpaEntity;
import be.kdg.team11.player.adapter.out.mapper.GameLobbyJpaMapper;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.port.out.LoadLobbyPort;
import be.kdg.team11.player.port.out.SaveGameLobbyPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LobbyJpaAdapter implements SaveGameLobbyPort, LoadLobbyPort, LoadGameLobbiesPort {
    private final GameLobbyJpaRepository gameLobbyJpaRepository;
    private final GameLobbyJpaMapper gameLobbyJpaMapper;

    public LobbyJpaAdapter(
            GameLobbyJpaRepository gameLobbyJpaRepository,
            GameLobbyJpaMapper gameLobbyJpaMapper
    ) {
        this.gameLobbyJpaRepository = gameLobbyJpaRepository;
        this.gameLobbyJpaMapper = gameLobbyJpaMapper;
    }

    @Override
    public List<Lobby> loadAll() {
        return gameLobbyJpaRepository.findAll().stream()
                .map(gameLobbyJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Lobby> loadBy(LobbyId lobbyId) {
        return gameLobbyJpaRepository.findById(lobbyId.gameLobbyId())
                .map(gameLobbyJpaMapper::toDomain);
    }

    @Override
    public Lobby save(Lobby lobby) {
        GameLobbyJpaEntity entity = gameLobbyJpaMapper.toJpaEntity(lobby);
        GameLobbyJpaEntity saved = gameLobbyJpaRepository.save(entity);
        return gameLobbyJpaMapper.toDomain(saved);
    }
}

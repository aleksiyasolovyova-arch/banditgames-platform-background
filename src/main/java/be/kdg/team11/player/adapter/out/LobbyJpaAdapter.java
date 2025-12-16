package be.kdg.team11.player.adapter.out;

import be.kdg.team11.player.adapter.out.jpa.LobbyJpaRepository;
import be.kdg.team11.player.adapter.out.jpa.entity.LobbyJpaEntity;
import be.kdg.team11.player.adapter.out.mapper.LobbyJpaMapper;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.port.out.LoadLobbyPort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LobbyJpaAdapter implements SaveLobbyPort, LoadLobbyPort {
    private final LobbyJpaRepository lobbyJpaRepository;
    private final LobbyJpaMapper lobbyJpaMapper;

    public LobbyJpaAdapter(
            LobbyJpaRepository lobbyJpaRepository,
            LobbyJpaMapper lobbyJpaMapper
    ) {
        this.lobbyJpaRepository = lobbyJpaRepository;
        this.lobbyJpaMapper = lobbyJpaMapper;
    }

    @Override
    public Optional<Lobby> loadBy(LobbyId lobbyId) {
        return lobbyJpaRepository.findById(lobbyId.lobbyId())
                .map(lobbyJpaMapper::toDomain);
    }

    @Override
    public Lobby save(Lobby lobby) {
        LobbyJpaEntity entity = lobbyJpaMapper.toJpaEntity(lobby);
        LobbyJpaEntity saved = lobbyJpaRepository.save(entity);
        return lobbyJpaMapper.toDomain(saved);
    }
}

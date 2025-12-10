package be.kdg.team11.player.adapter.out;

import org.springframework.stereotype.Service;
import be.kdg.team11.player.adapter.out.jpa.entity.PlayerJpaEntity;
import be.kdg.team11.player.adapter.out.jpa.PlayerJpaRepository;
import be.kdg.team11.player.adapter.out.mapper.PlayerJpaMapper;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.out.SavePlayerPort;

@Service
public class PlayerJpaAdapter implements SavePlayerPort{
    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerJpaMapper playerJpaMapper;

    public PlayerJpaAdapter(PlayerJpaRepository playerJpaRepository, PlayerJpaMapper playerJpaMapper) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerJpaMapper = playerJpaMapper;
    }
    @Override
    public Player save(Player player) {
        PlayerJpaEntity entity = playerJpaMapper.toJpaEntity(player);
        PlayerJpaEntity savedEntity = playerJpaRepository.save(entity);
        return playerJpaMapper.toDomain(savedEntity);
    }
}

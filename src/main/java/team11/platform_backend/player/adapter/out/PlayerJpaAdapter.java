package team11.platform_backend.player.adapter.out;

import org.springframework.stereotype.Service;
import team11.platform_backend.player.adapter.out.jpa.entity.PlayerJpaEntity;
import team11.platform_backend.player.adapter.out.jpa.PlayerJpaRepository;
import team11.platform_backend.player.adapter.out.mapper.PlayerJpaMapper;
import team11.platform_backend.player.domain.player.Player;
import team11.platform_backend.player.port.out.SavePlayerPort;

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

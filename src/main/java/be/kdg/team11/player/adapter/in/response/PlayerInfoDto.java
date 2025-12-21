package be.kdg.team11.player.adapter.in.response;

import java.util.UUID;

public record PlayerInfoDto(
        UUID playerId,
        String name,
        String pictureUrl
) {
}

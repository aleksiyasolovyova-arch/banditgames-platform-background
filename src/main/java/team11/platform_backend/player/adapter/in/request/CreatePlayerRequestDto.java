package team11.platform_backend.player.adapter.in.request;

import team11.platform_backend.player.domain.player.PlayerId;

import java.util.UUID;

public record CreatePlayerRequestDto(
        PlayerId playerId
) {
}

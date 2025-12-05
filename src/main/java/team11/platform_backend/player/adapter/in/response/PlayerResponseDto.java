package team11.platform_backend.player.adapter.in.response;

import team11.platform_backend.player.domain.player.PlayerId;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerResponseDto(
        PlayerId playerId,
        LocalDate joinedDate
) {
}

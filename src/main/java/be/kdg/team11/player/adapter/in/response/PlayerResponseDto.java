package be.kdg.team11.player.adapter.in.response;

import be.kdg.team11.player.domain.player.PlayerId;

import java.time.LocalDate;

public record PlayerResponseDto(
        PlayerId playerId,
        LocalDate joinedDate
) {
}

package be.kdg.team11.player.adapter.in.request;

import be.kdg.team11.player.domain.player.PlayerId;

public record CreatePlayerRequestDto(
        PlayerId playerId
) {
}

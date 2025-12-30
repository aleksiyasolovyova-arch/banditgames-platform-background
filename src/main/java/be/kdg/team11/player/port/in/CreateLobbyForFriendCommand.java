package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.PlayerId;

import java.util.UUID;

public record CreateLobbyForFriendCommand(
        PlayerId playerId, UUID gameId, PlayerId friendId
) {
}

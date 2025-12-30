package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.PlayerId;

import java.util.UUID;

public record CreateLobbyForStrangerCommand(
        PlayerId playerId, UUID gameId, String strangerUserName
) {
}

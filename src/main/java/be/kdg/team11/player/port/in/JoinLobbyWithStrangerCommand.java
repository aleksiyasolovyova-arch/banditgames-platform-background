package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;

public record JoinLobbyWithStrangerCommand(
        GameReference gameReference, PlayerId playerId
) {
}

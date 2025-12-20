package be.kdg.team11.player.port.in;

import java.util.UUID;

public record ChangePlayerPictureUrlCommand(
        UUID playerId,
        String pictureUrl
) {
}

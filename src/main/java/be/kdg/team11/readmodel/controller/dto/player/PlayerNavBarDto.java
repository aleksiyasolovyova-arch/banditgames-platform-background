package be.kdg.team11.readmodel.controller.dto.player;

import java.util.UUID;

public record PlayerNavBarDto(
        UUID playerID,
        String username,
        String pictureUrl
) {
}

package be.kdg.team11.readmodel.controller.dto.player;

import java.util.UUID;

public record PlayerModelNavBarDto(
        UUID playerID,
        String username,
        String pictureUrl
) implements PlayerModelDto {
}

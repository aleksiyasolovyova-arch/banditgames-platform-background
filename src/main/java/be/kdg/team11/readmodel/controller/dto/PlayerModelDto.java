package be.kdg.team11.readmodel.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerModelDto(
        UUID playerID,
        String username,
        String pictureUrl,
        LocalDate joinedDate,
        UUID favouriteGameId
) {
}

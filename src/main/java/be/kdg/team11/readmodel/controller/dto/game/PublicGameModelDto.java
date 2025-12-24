package be.kdg.team11.readmodel.controller.dto.game;

import java.util.UUID;

public record PublicGameModelDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameCreatorName
) implements GameModelDto {
}


package be.kdg.team11.content.adapter.in.response;

import java.util.UUID;

public record PublicGameDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameCreatorName
) implements GameDto {
}

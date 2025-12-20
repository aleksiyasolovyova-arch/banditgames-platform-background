package be.kdg.team11.readmodel.controller.response;

import java.util.List;
import java.util.UUID;

public record PlayerGamesDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<RuleDto> rules,
        boolean isFavourite
) {
    public record RuleDto(
            String description
    ) {
    }
}

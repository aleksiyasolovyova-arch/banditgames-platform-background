package be.kdg.team11.readmodel.controller.dto;

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
        boolean isFavourite,
        boolean playableWithAi
) implements GameDto{
    public record RuleDto(
            String description
    ) {
    }
}

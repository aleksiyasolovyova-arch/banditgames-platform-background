package be.kdg.team11.readmodel.controller.dto.game;

import java.util.List;
import java.util.UUID;

public record PlayerGameModelDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<RuleDto> rules,
        boolean isFavourite,
        boolean playableWithAi
) implements GameModelDto {
    public record RuleDto(
            String description
    ) {
    }
}

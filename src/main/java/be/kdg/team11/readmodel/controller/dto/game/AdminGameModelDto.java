package be.kdg.team11.readmodel.controller.dto.game;

import java.util.List;
import java.util.UUID;

public record AdminGameModelDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<RuleDto> rules,
        List<GameAchievementDto> achievements,
        boolean playableWithAI,
        boolean pending
) implements GameModelDto {
    public record RuleDto(
            String description
    ) {
    }

    public record GameAchievementDto(
            String code,
            String description
    ) {
    }
}

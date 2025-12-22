package be.kdg.team11.readmodel.controller.dto;

import java.util.List;
import java.util.UUID;

public record AdminGameDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        String registrationState,
        List<RuleDto> rules,
        List<GameAchievementDto> achievements,
        boolean playableWithAI
) implements GameDto {
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

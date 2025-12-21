package be.kdg.team11.content.adapter.in.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record GameDto(
        UUID gameId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        String registrationState,
        List<RuleDto> rules,
        List<GameAchievementDto> achievements,
        boolean playableWithAI
) {
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

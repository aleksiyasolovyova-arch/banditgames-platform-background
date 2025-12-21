package be.kdg.team11.content.port.in;

import java.math.BigDecimal;
import java.util.List;

public record RegisterGameCommand(
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<String> rules,
        List<GameAchievementCommand> achievements,
        boolean playableWithAI
) {
    public record GameAchievementCommand(
            String code,
            String description
    ) {
    }
}

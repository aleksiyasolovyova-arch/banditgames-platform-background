package be.kdg.team11.content.port.in;

import java.util.List;

public record RegisterGameCommand(
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<String> rules,
        List<GameAchievementCommand> gameAchievements,
        boolean playableWithAI
) {
    public record GameAchievementCommand(
            String code,
            String description
    ) {
    }
}

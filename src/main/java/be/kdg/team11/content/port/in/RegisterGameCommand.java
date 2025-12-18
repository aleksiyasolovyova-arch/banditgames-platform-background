package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;

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
        List<GameAchievementCommand> achievements
) {
    public record GameAchievementCommand(
            String code,
            String description
    ) {
    }
}

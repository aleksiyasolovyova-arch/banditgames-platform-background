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
    public RegisterGameCommand {
        if (name == null || name.isBlank()) {
            throw new InvalidGameDataException("Game name cannot be empty");
        }
        if (name.length() > 100) {
            throw new InvalidGameDataException("Game name cannot exceed 100 characters");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidGameDataException("Game description cannot be empty");
        }
        if (description.length() > 500) {
            throw new InvalidGameDataException("Game description cannot exceed 500 characters");
        }
        if (price == null) {
            throw new InvalidGameDataException("Game price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidGameDataException("Game price cannot be negative");
        }
        if (pictureUrl == null || pictureUrl.isBlank()) {
            throw new InvalidGameUrlException("Picture URL cannot be empty");
        }
        if (gameUrl == null || gameUrl.isBlank()) {
            throw new InvalidGameUrlException("Game URL cannot be empty");
        }
        if (gameCreatorName == null || gameCreatorName.isBlank()) {
            throw new InvalidGameDataException("Game creator name cannot be empty");
        }
        if (rules == null || rules.isEmpty()) {
            throw new InvalidGameDataException("At least one rule must be provided");
        }
    }

    public record GameAchievementCommand(
            String code,
            String description
    ) {
    }
}

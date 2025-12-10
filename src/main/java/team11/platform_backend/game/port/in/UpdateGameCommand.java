package team11.platform_backend.game.port.in;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateGameCommand(
        UUID gameId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<String> rules,
        List<GameAchievementCommand> achievements
) {
    public UpdateGameCommand {
        // Game ID
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        // Game Name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }
        name = name.trim();
        if (name.length() > 100) {
            throw new IllegalArgumentException("Game name cannot exceed 100 characters");
        }

        // Game Description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Game description cannot be empty");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Game description cannot exceed 500 characters");
        }

        // Game Price
        if (price == null) {
            throw new IllegalArgumentException("Game price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Game price cannot be negative");
        }

        // Picture URLs
        if (pictureUrl == null || pictureUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Picture URL cannot be empty");
        }

        // Game URL
        if (gameUrl == null || gameUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Game URL cannot be empty");
        }

        // Game Creator Name
        if (gameCreatorName == null || gameCreatorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game creator name cannot be empty");
        }

        // Rules
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("At least one rule must be provided");
        }

    }

    public record GameAchievementCommand(
            String code,
            String description
    ) {}
}

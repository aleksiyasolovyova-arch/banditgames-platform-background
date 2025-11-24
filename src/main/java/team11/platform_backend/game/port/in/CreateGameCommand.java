package team11.platform_backend.game.port.in;

import java.math.BigDecimal;
import java.util.List;

public record CreateGameCommand(
        String gameName,
        String gameDescription,
        BigDecimal gamePrice,
        List<String> pictureUrls,
        String gameCreatorName,
        String gameUrl,
        List<RuleCommand> rules,
        List<AchievementCommand> achievements
        //String aiPlayerUrl
) {
    public CreateGameCommand {
        // Game Name
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }
        gameName = gameName.trim();
        if (gameName.length() > 100) {
            throw new IllegalArgumentException("Game name cannot exceed 100 characters");
        }

        // Game Description
        if (gameDescription == null || gameDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Game description cannot be empty");
        }
        if (gameDescription.length() > 500) {
            throw new IllegalArgumentException("Game description cannot exceed 500 characters");
        }

        // Game Price
        if (gamePrice == null) {
            throw new IllegalArgumentException("Game price cannot be null");
        }
        if (gamePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Game price cannot be negative");
        }

        // Picture URLs
        if (pictureUrls == null || pictureUrls.isEmpty()) {
            throw new IllegalArgumentException("At least one picture URL must be provided");
        }

        // Game Creator Name
        if (gameCreatorName == null || gameCreatorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game creator name cannot be empty");
        }

        // Game URL
        if (gameUrl == null || gameUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Game URL cannot be empty");
        }

        // Rules
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("At least one rule must be provided");
        }

        // Achievements
        if (achievements == null || achievements.isEmpty()) {
            throw new IllegalArgumentException("At least one achievement must be provided");
        }
    }

    public record RuleCommand(
            String ruleName,
            String ruleDescription,
            List<String> ruleCategories // SETUP, GAME_PLAY, WINNING
    ) {}

    public record AchievementCommand(
            String achievementName,
            String achievementDescription,
            String pictureUrl,
            String achievementType, // GAME_PLAYED, GAME_WON, etc.
            BigDecimal threshold
    ) {}
}

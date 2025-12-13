package be.kdg.team11.content.port.in;

import org.springframework.util.Assert;

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
        Assert.hasText(name, "Game name cannot be empty");
        Assert.isTrue(name.length() <= 100, "Game name cannot exceed 100 characters");
        Assert.hasText(description, "Game description cannot be empty");
        Assert.isTrue(description.length() <= 500, "Game description cannot exceed 500 characters");
        Assert.notNull(price, "Game price cannot be null");
        Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Game price cannot be negative");
        Assert.hasText(pictureUrl, "Picture URL cannot be empty");
        Assert.hasText(gameUrl, "Game URL cannot be empty");
        Assert.hasText(gameCreatorName, "Game creator name cannot be empty");
        Assert.notEmpty(rules, "At least one rule must be provided");
    }

    public record GameAchievementCommand(
            String code,
            String description
    ) {
    }
}

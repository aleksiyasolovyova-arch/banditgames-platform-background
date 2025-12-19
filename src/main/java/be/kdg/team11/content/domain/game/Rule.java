package be.kdg.team11.content.domain.game;

/**
 * Value Object representing a game rule.
 * Immutable rule text that describes how a game should be played.
 */
public record Rule(
        String description
) {

    public static Rule of(String description) {
        return new Rule(description);
    }
}

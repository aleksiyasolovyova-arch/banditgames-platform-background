package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;

/**
 * Value Object representing a game rule.
 * Immutable rule text that describes how a game should be played.
 */
 public record Rule(
        String description
){
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final int MIN_DESCRIPTION_LENGTH = 5;

    public Rule {
        if (description == null) {
            throw new InvalidGameDataException("Rule description cannot be null");
        }

        if (description.isBlank()) {
            throw new InvalidGameDataException("Rule description cannot be empty or whitespace only");
        }

        if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidGameDataException(
                    "Rule description must be between " + MIN_DESCRIPTION_LENGTH + " and " +
                            MAX_DESCRIPTION_LENGTH + " characters, received: " + description.length()
            );
        }
    }
}

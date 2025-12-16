package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;

public record GameAchievement(
        String code,
        String description
) {
    private static final int MAX_CODE_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    public GameAchievement {
        validateAchievementCode(code);
        validateAchievementDescription(description);
    }
    public static GameAchievement of(String code, String description) {
        return new GameAchievement(code, description);
    }

    private static void validateAchievementCode(String code) {
        if (code == null || code.isBlank()) {
            throw new InvalidGameDataException("Achievement code cannot be null or empty");
        }
        if (code.length() > MAX_CODE_LENGTH) {
            throw new InvalidGameDataException(
                    "Achievement code must not exceed " + MAX_CODE_LENGTH + " characters, received: " + code.length()
            );
        }
    }

    private static void validateAchievementDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidGameDataException("Achievement description cannot be null or empty");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidGameDataException(
                    "Achievement description must not exceed " + MAX_DESCRIPTION_LENGTH + " characters, received: " + description.length()
            );
        }
    }
}

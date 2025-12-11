package be.kdg.team11.content.domain.achievement.exeptions;

/**
 * Thrown when achievement type validation fails.
 * Examples: null achievement type, type doesn't support statistics
 */
public class InvalidAchievementTypeException extends AchievementException {
    public InvalidAchievementTypeException(String message) {
        super(message);
    }
}

package be.kdg.team11.content.domain.platformachievement.exeptions;

/**
 * Thrown when achievement type validation fails.
 * Examples: null achievement type, type doesn't support statistics
 */
public class InvalidPlatformAchievementTypeException extends PlatformAchievementException {
    public InvalidPlatformAchievementTypeException(String message) {
        super(message);
    }
}

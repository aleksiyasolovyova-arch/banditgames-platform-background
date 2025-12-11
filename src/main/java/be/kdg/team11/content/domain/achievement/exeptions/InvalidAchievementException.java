package be.kdg.team11.content.domain.achievement.exeptions;

/**
 * Thrown when achievement data violates domain invariants.
 * Examples: null name, invalid required value, missing picture URL
 */

public class InvalidAchievementException extends AchievementException {
    public InvalidAchievementException(String message) {
        super(message);
    }
}

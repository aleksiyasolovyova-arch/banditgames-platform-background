package be.kdg.team11.content.domain.platformachievement.exeptions;

/**
 * Thrown when achievement data violates domain invariants.
 * Examples: null name, invalid required value, missing picture URL
 */

public class InvalidPlatformAchievementException extends PlatformAchievementException {
    public InvalidPlatformAchievementException(String message) {
        super(message);
    }
}

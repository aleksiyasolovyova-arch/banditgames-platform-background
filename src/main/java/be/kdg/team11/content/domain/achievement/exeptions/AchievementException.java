package be.kdg.team11.content.domain.achievement.exeptions;

/**
 * Base exception for achievement domain violations.
 * Used when business rules related to achievements are broken.
 */
public class AchievementException extends RuntimeException {
    public AchievementException(String message) {
        super(message);
    }

    public AchievementException(String message, Throwable cause) {
        super(message, cause);
    }
}

package be.kdg.team11.content.domain.platformachievement.exeptions;

/**
 * Base exception for achievement domain violations.
 * Used when business rules related to gameAchievements are broken.
 */
public class PlatformAchievementException extends RuntimeException {
    public PlatformAchievementException(String message) {
        super(message);
    }

    public PlatformAchievementException(String message, Throwable cause) {
        super(message, cause);
    }
}

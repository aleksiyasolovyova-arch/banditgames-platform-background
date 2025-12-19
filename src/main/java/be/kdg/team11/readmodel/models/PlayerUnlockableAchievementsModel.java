package be.kdg.team11.readmodel.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlayerUnlockableAchievementsModel(
        UUID playerId,

        String achievementType,

        // Shared achievement fields
        String achievementDescription,
        boolean unlocked,
        LocalDateTime unlockedAt,

        // Game achievement fields
        UUID gameId,
        String gameName,
        String gameAchievementCode,

        // Platform achievement fields
        UUID platformAchievementId,
        String platformAchievementName,
        String platformAchievementPictureUrl,
        String platformAchievementType,
        long platformAchievementRequiredValue

        /** Calculated values
         * player specific stat / required value for the achievments ( count of friendships divided by the required friendships for an achievement )
         * overall progress ( out of all possible achievements how many are unlocked ) ( one for percentage one for count )
         */
) {
}

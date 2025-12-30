package be.kdg.team11.content.domain.platformachievement;

import be.kdg.team11.content.domain.platformachievement.exeptions.InvalidPlatformAchievementException;
import be.kdg.team11.content.domain.projections.PlayerStatistics;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate Root for the Achievement subdomain.
 * Represents a global achievement that can be earned by any player.
 */
public class PlatformAchievement {
    private final PlatformAchievementId platformAchievementId;
    private final String name;
    private final String description;
    private final String pictureUrl;
    private final PlatformAchievementType type;
    private final long requiredValue;

    private final List<DomainEvent> eventStore = new ArrayList<>();

    public PlatformAchievement(PlatformAchievementId platformAchievementId, String name, String description, String pictureUrl, PlatformAchievementType type, long requiredValue) {
        this.platformAchievementId = platformAchievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.requiredValue = requiredValue;
    }


    public static PlatformAchievement create(String name, String description, String pictureUrl, PlatformAchievementType type, long requiredValue) {
        validatePlatformAchievementRequiredValue(requiredValue);

        PlatformAchievement platformAchievement = new PlatformAchievement(
                PlatformAchievementId.create(),
                name,
                description,
                pictureUrl,
                type,
                requiredValue
        );

        PlatformAchievementCreatedEvent event = new PlatformAchievementCreatedEvent(
                platformAchievement.platformAchievementId.achievementId(),
                name,
                description,
                pictureUrl,
                type.name(),
                requiredValue
        );
        platformAchievement.eventStore.add(event);

        return platformAchievement;
    }

    /**
     * Evaluates if a player has met the criteria for this specific achievement.
     * Delegates to the achievement type to determine if the required value is met.
     */
    public boolean isMet(PlayerStatistics statistics) {
        return type.isMetBy(requiredValue, statistics);
    }


    private static void validatePlatformAchievementRequiredValue(long requiredValue) {
        if (requiredValue < 0) {
            throw new InvalidPlatformAchievementException(
                    "Required value cannot be negative, received: " + requiredValue
            );
        }
    }

    public PlatformAchievementId getAchievementId() {
        return platformAchievementId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public PlatformAchievementType getType() {
        return type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }

    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}

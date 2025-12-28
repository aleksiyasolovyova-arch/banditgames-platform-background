package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate Root for the Achievement subdomain.
 * Represents a global achievement that can be earned by any player.
 */
public class Achievement {
    private final AchievementId achievementId;
    private final String name;
    private final String description;
    private final String pictureUrl;
    private final AchievementType type;
    private final long requiredValue;

    private final List<DomainEvent> eventStore = new ArrayList<>();

    public Achievement(AchievementId achievementId, String name, String description, String pictureUrl, AchievementType type, long requiredValue) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.requiredValue = requiredValue;
    }


    public static Achievement create(String name, String description, String pictureUrl, AchievementType type, long requiredValue) {
        validateAchievementRequiredValue(requiredValue);

        Achievement achievement = new Achievement(
                AchievementId.create(),
                name,
                description,
                pictureUrl,
                type,
                requiredValue
        );

        AchievementCreatedEvent event = new AchievementCreatedEvent(
                achievement.achievementId.achievementId(),
                name,
                description,
                pictureUrl,
                type.name(),
                requiredValue
        );
        achievement.eventStore.add(event);

        return achievement;
    }

    /**
     * Evaluates if a player has met the criteria for this specific achievement.
     * Delegates to the achievement type to determine if the required value is met.
     */
    public boolean isAchievementMet(long actualValue) {
        return type.isMetBy(requiredValue, actualValue);
    }


    private static void validateAchievementRequiredValue(long requiredValue) {
        if (requiredValue < 0) {
            throw new InvalidAchievementException(
                    "Required value cannot be negative, received: " + requiredValue
            );
        }
    }

    public AchievementId getAchievementId() {
        return achievementId;
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

    public AchievementType getType() {
        return type;
    }

    public long getRequiredValue() {
        return requiredValue;
    }

    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}

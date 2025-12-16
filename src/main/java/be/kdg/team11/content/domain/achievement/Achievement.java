package be.kdg.team11.content.domain.achievement;

import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementTypeException;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;
import be.kdg.team11.sharedkernel.events.DomainEvent;

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
    private final Url pictureUrl;
    private final AchievementType type;
    private final long requiredValue;

    private final List<DomainEvent> eventStore = new ArrayList<>();

    public Achievement(AchievementId achievementId, String name, String description, Url pictureUrl, AchievementType type, long requiredValue) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.requiredValue = requiredValue;
    }


    public static Achievement create (String name, String description, Url pictureUrl, AchievementType type, long requiredValue) {
        validateAchievementName(name);
        validateAchievementDescription(description);
        validateAchievementPictureUrl(pictureUrl);
        validateAchievementType(type);
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
                pictureUrl.toString(),
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
    public boolean isAchievementMet(PlayerStatistics statistics) {
        if (statistics == null) {
            throw new InvalidAchievementException(
                    "Player statistics cannot be null when evaluating achievement"
            );
        }

        return type.isMetBy(requiredValue, statistics);
    }


    private static void validateAchievementName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidAchievementException(
                    "Achievement name cannot be null or empty"
            );
        }
        if (name.length() > 100) {
            throw new InvalidAchievementException(
                    "Achievement name cannot exceed 100 characters, received: " + name.length()
            );
        }
    }

    private static void validateAchievementDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidAchievementException(
                    "Achievement description cannot be null or empty"
            );
        }
        if (description.length() > 500) {
            throw new InvalidAchievementException(
                    "Achievement description cannot exceed 500 characters, received: " + description.length()
            );
        }
    }
    private static void validateAchievementPictureUrl(Url pictureUrl) {
        if (pictureUrl == null) {
            throw new InvalidAchievementException(
                    "Achievement picture URL cannot be null"
            );
        }
    }

    private static void validateAchievementType(AchievementType type) {
        if (type == null) {
            throw new InvalidAchievementTypeException(
                    "Achievement type cannot be null"
            );
        }
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

    public Url getPictureUrl() {
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

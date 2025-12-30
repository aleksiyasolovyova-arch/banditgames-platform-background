package be.kdg.team11.content.domain.projections;


import be.kdg.team11.content.domain.platformachievement.PlatformAchievementId;
import be.kdg.team11.content.domain.projections.exceptions.PlayerStatisticsNotFoundException;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementUnlockedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerStatistics {
    private final UUID playerId;
    List<PlatformAchievementId> unlockedPlatformAchievements = new ArrayList<>();
    private long totalGamesPlayed;
    private long totalWins;
    private long totalFriends;
    private long bestRecordTime;

    private final List<DomainEvent> eventStore = new ArrayList<>();

    public PlayerStatistics(UUID playerId, long totalGamesPlayed, long totalWins, long totalFriends, long bestRecordTime, List<PlatformAchievementId> unlockedPlatformAchievements) {
        this.playerId = playerId;
        this.totalGamesPlayed = totalGamesPlayed;
        this.totalWins = totalWins;
        this.totalFriends = totalFriends;
        this.bestRecordTime = bestRecordTime;
        this.unlockedPlatformAchievements.addAll(unlockedPlatformAchievements);
    }

    public static PlayerStatistics create(
            UUID playerId
    ) {
        return new PlayerStatistics(playerId, 0, 0, 0, Long.MAX_VALUE, Collections.emptyList());
    }

    public void unlockPlatformAchievement(PlatformAchievementId platformAchievementId) {
        if (unlockedPlatformAchievements.contains(platformAchievementId)) {
            return;
        }
        unlockedPlatformAchievements.add(platformAchievementId);
        PlatformAchievementUnlockedEvent event = new PlatformAchievementUnlockedEvent(playerId, platformAchievementId.achievementId());
        this.eventStore.add(event);
    }

    public void addGamePlayed() {
        totalGamesPlayed++;
    }

    public void addWin() {
        totalGamesPlayed++;
        totalWins++;
    }

    //todo should that be added int the befriend use case of not used at all
    public void addFriend() {
        totalFriends++;
    }

    public void setBestRecordTime(long time) {
        if (time < this.bestRecordTime) {
            bestRecordTime = time;
        }
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public List<PlatformAchievementId> getUnlockedPlatformAchievements() {
        return Collections.unmodifiableList(unlockedPlatformAchievements);
    }

    public long getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public long getTotalFriends() {
        return totalFriends;
    }

    public long getBestRecordTime() {
        return bestRecordTime;
    }

    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }

    public static PlayerStatisticsNotFoundException notFound(UUID playerId) {
        return new PlayerStatisticsNotFoundException(
                String.format("Player statistics not found for player with ID: %s", playerId)
        );
    }
}
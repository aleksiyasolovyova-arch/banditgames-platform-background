package be.kdg.team11.content.domain.projections;


import be.kdg.team11.content.domain.achievement.AchievementId;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.achievement.AchievementUnlockedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerStatistics{
    private final UUID playerId;
    List<AchievementId> unlockedAchievements = new ArrayList<>();
    private long totalGamesPlayed;
    private long totalWins;
    private long totalFriends;
    private long bestRecordTime;

    private final List<DomainEvent> eventStore = new ArrayList<>();

    public PlayerStatistics(UUID playerId, long totalGamesPlayed, long totalWins, long totalFriends, long bestRecordTime, List<AchievementId> unlockedAchievements) {
        this.playerId = playerId;
        this.totalGamesPlayed = totalGamesPlayed;
        this.totalWins = totalWins;
        this.totalFriends = totalFriends;
        this.bestRecordTime = bestRecordTime;
        this.unlockedAchievements.addAll(unlockedAchievements);
    }

    public static PlayerStatistics create(
            UUID playerId
    ) {
        return new PlayerStatistics(playerId,0,0,0,Long.MAX_VALUE, Collections.emptyList());
    }

    public void unlockAchievement(AchievementId achievementId) {
        if (unlockedAchievements.contains(achievementId)) {
            return;
        }
        unlockedAchievements.add(achievementId);
        AchievementUnlockedEvent event = new AchievementUnlockedEvent(playerId, achievementId.achievementId());
        this.eventStore.add(event);
    }

    public void addGamePlayed(){
        totalGamesPlayed++;
    }

    public void addWin(){
        totalWins++;
    }

    public void addFriend(){
        totalFriends++;
    }

    public void setBestRecordTime(long time){
        bestRecordTime = time;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public List<AchievementId> getUnlockedAchievements() {
        return Collections.unmodifiableList(unlockedAchievements);
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
}
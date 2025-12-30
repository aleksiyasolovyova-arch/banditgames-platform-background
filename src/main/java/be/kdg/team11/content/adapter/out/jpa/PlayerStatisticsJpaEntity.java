package be.kdg.team11.content.adapter.out.jpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "content_schema")
public class PlayerStatisticsJpaEntity {
    @Id
    private UUID playerId;

    @Column
    private long totalGamesPlayed;

    @Column
    private long totalWins;

    @Column
    private long totalFriends;

    @Column
    private long bestRecordTime;

    @ElementCollection
    @CollectionTable(
            name = "unlocked_achievements",
            schema = "content_schema",
            joinColumns = @JoinColumn(name = "player_id")
    )
    @Column
    private List<UUID> unlockedAchievements = new ArrayList<>();

    public PlayerStatisticsJpaEntity() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public long getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(long totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(long totalWins) {
        this.totalWins = totalWins;
    }

    public long getTotalFriends() {
        return totalFriends;
    }

    public void setTotalFriends(long totalFriends) {
        this.totalFriends = totalFriends;
    }

    public long getBestRecordTime() {
        return bestRecordTime;
    }

    public void setBestRecordTime(long bestRecordTime) {
        this.bestRecordTime = bestRecordTime;
    }

    public List<UUID> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    public void setUnlockedAchievements(List<UUID> unlockedAchievements) {
        this.unlockedAchievements = unlockedAchievements;
    }
}

package be.kdg.team11.player.adapter.out.jpa.entity;

import be.kdg.team11.player.adapter.out.jpa.embeddable.OwnedGameEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedGameAchievementEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedPlatformAchievementEmbeddable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "player_schema")
public class PlayerJpaEntity {
    @Id
    private UUID playerId;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @ElementCollection
    @CollectionTable(schema = "player_schema")
    private Set<UnlockedPlatformAchievementEmbeddable> unlockedPlatformAchievements = new HashSet<>();

    @ElementCollection
    @CollectionTable(schema = "player_schema")
    private Set<UnlockedGameAchievementEmbeddable> unlockedGameAchievements = new HashSet<>();

    @ElementCollection
    @CollectionTable(schema = "player_schema")
    private Set<OwnedGameEmbeddable> ownedGames = new HashSet<>();

    public PlayerJpaEntity() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Set<UnlockedPlatformAchievementEmbeddable> getUnlockedPlatformAchievements() {
        return unlockedPlatformAchievements;
    }

    public void setUnlockedPlatformAchievements(Set<UnlockedPlatformAchievementEmbeddable> unlockedPlatformAchievements) {
        this.unlockedPlatformAchievements = unlockedPlatformAchievements;
    }

    public Set<UnlockedGameAchievementEmbeddable> getUnlockedGameAchievements() {
        return unlockedGameAchievements;
    }

    public void setUnlockedGameAchievements(Set<UnlockedGameAchievementEmbeddable> unlockedGameAchievements) {
        this.unlockedGameAchievements = unlockedGameAchievements;
    }

    public Set<OwnedGameEmbeddable> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(Set<OwnedGameEmbeddable> ownedGames) {
        this.ownedGames = ownedGames;
    }
}
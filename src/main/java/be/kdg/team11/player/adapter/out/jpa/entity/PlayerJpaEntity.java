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
@Table(name = "player", schema = "player_schema")
public class PlayerJpaEntity {
    @Id
    private UUID playerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @ElementCollection
    @CollectionTable(name = "player_platform_achievements", schema = "player_schema", joinColumns = @JoinColumn(name = "player_id"))
    private Set<UnlockedPlatformAchievementEmbeddable> unlockedPlatformAchievements = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "player_game_achievements", schema = "player_schema", joinColumns = @JoinColumn(name = "player_id"))
    private Set<UnlockedGameAchievementEmbeddable> unlockedGameAchievements = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "player_owned_games", schema = "player_schema", joinColumns = @JoinColumn(name = "player_id"))
    private Set<OwnedGameEmbeddable> ownedGames = new HashSet<>();

    public PlayerJpaEntity() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
package team11.platform_backend.player.adapter.out.jpa;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "players", schema = "player_schema")
public class PlayerJpaEntity {

    @Id
    @Column(name = "player_id", columnDefinition = "UUID")
    private UUID playerId;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "unlocked_platform_achievements", schema = "player_schema",
            joinColumns = @JoinColumn(name = "player_id"))
    private Set<UnlockedPlatformAchievementEmbeddable> unlockedPlatformAchievements = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "unlocked_game_achievements", schema = "player_schema",
            joinColumns = @JoinColumn(name = "player_id"))
    private Set<UnlockedGameAchievementEmbeddable> unlockedGameAchievements = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "owned_games", schema = "player_schema",
            joinColumns = @JoinColumn(name = "player_id"))
    private Set<OwnedGameEmbeddable> ownedGames = new HashSet<>();

    public PlayerJpaEntity() {}

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
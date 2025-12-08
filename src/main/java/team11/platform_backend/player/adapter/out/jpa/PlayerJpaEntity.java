package team11.platform_backend.player.adapter.out.jpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerJpaEntity {
    @Id
    @Column(name = "player_id", columnDefinition = "UUID")
    private UUID playerId;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    //@ElementCollection
    //@CollectionTable(name = "player_unlocked_global_achievements", joinColumns = @JoinColumn(name = "player_id"))
    //@Column(name = "achievement_code")
    //private Set<String> unlockedGlobalAchievements = new HashSet<>();
//
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@JoinColumn(name = "player_id")
    //private Set<UnlockedGameAchievementJpaEntity> unlockedGameAchievements = new HashSet<>();
//
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@JoinColumn(name = "player_id")
    //private Set<OwnedGameJpaEntity> ownedGames = new HashSet<>();


    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID playerId, LocalDate joinedDate) {
        this.playerId = playerId;
        this.joinedDate = joinedDate;
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
}

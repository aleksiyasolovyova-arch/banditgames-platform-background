package team11.platform_backend.player.domain.player;

import team11.platform_backend.player.domain.projections.GameId;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final PlayerId playerId;
    private final LocalDate joinedDate;
    private final Set<UnlockedPlatformAchievement> unlockedPlatformAchievements = new HashSet<>();
    private final Set<UnlockedGameAchievement> unlockedGameAchievements = new HashSet<>();
    private final Set<OwnedGame> ownedGames = new HashSet<>();

    public Player(PlayerId playerId, LocalDate joinedDate, Set<UnlockedPlatformAchievement> unlockedPlatformAchievements, Set<UnlockedGameAchievement> unlockedGameAchievements, Set<OwnedGame> ownedGames) {
        this.playerId = playerId;
        this.joinedDate = joinedDate;
        this.unlockedPlatformAchievements.addAll(unlockedPlatformAchievements);
        this.unlockedGameAchievements.addAll(unlockedGameAchievements);
        this.ownedGames.addAll(ownedGames);
    }

    // for creating (post method)
    public Player(PlayerId playerId) {
        this.playerId = playerId;
        this.joinedDate = LocalDate.now();
    }

    public void addOwnedGame(GameId gameId) {
        ownedGames.add(new OwnedGame(gameId, false, LocalDate.now()));
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public Set<UnlockedPlatformAchievement> getUnlockedPlatformAchievements() {
        return unlockedPlatformAchievements;
    }

    public Set<UnlockedGameAchievement> getUnlockedGameAchievements() {
        return unlockedGameAchievements;
    }

    public Set<OwnedGame> getOwnedGames() {
        return ownedGames;
    }
}
